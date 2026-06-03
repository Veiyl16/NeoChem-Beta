package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.NeoChemApplication
import com.example.data.model.MateriProgress
import com.example.data.model.UserAchievement
import com.example.data.model.UserProfile
import com.example.data.repository.FirebaseRepository
import com.example.data.repository.FirestoreLeaderboardEntry
import com.example.data.repository.FirestoreUser
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val email: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val localRepository = (application as NeoChemApplication).repository
    val firebaseRepository = (application as NeoChemApplication).firebaseRepository

    val avatars = listOf(
        "Atom Rover" to "Science Flask",
        "Quantum Flask" to "Graduation Cap",
        "Radon Cat" to "Golden Badge",
        "Proton Bot" to "Open Library",
        "Nano Hex" to "Brilliant Brain",
        "Cyber Kid" to "Lead Chemist"
    )

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Realtime Global Leaderboard Flow
    val leaderboardList: StateFlow<List<FirestoreLeaderboardEntry>> = firebaseRepository.getLeaderboardFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val firebaseUserProfileStream = firebaseRepository.streamUserProfile()
    private val localUserProfileStream = localRepository.userProfile

    // Unified User Profile Flow: Streams Firestore data if logged in, else falls back to Local Room profile seamlessly
    val userProfile: StateFlow<UserProfile?> = combine(
        firebaseUserProfileStream,
        localUserProfileStream
    ) { firebaseUser, localUser ->
        if (firebaseUser != null) {
            val photoUrlVal = firebaseUser.photoUrl ?: ""
            val idx = if (photoUrlVal.isNotEmpty() && photoUrlVal.startsWith("avatar_")) {
                photoUrlVal.substringAfter("avatar_").toIntOrNull() ?: 0
            } else {
                0
            }
            UserProfile(
                id = 1,
                username = if (!firebaseUser.name.isNullOrBlank()) firebaseUser.name ?: "SiswaNeo" else (firebaseUser.username ?: "SiswaNeo"),
                email = firebaseUser.email ?: "",
                xp = firebaseUser.xp ?: 0,
                level = firebaseUser.level ?: 1,
                lastLoginTimestamp = System.currentTimeMillis(),
                avatarIndex = idx,
                totalQuizzesCompleted = firebaseUser.totalQuiz ?: 0,
                totalSimulationsCompleted = 0,
                photoUrl = firebaseUser.photoUrl ?: "",
                coins = firebaseUser.coins ?: firebaseUser.coin ?: 100
            )
        } else {
            localUser
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val allAchievements: StateFlow<List<UserAchievement>> = localRepository.allAchievements
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allMateriProgress: StateFlow<List<MateriProgress>> = localRepository.allMateriProgress
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        // Auto check login session on startup
        val current = firebaseRepository.getCurrentUser()
        if (current != null) {
            _authState.value = AuthState.Success(current.email ?: "authenticated")
        }

        // Pipe all coinEventFlow DB actions directly to the view model flow & sync to Firestore if logged in
        viewModelScope.launch {
            com.example.data.repository.UserRepository.coinEventFlow.collect { amount ->
                _coinEarnedFlow.emit(amount)
                if (firebaseRepository.getCurrentUser() != null) {
                    try {
                        firebaseRepository.adjustCoins(amount)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        // Sync achievements from Firestore to local Room database in real-time if logged in
        viewModelScope.launch {
            firebaseUserProfileStream.collect { firebaseUser ->
                if (firebaseUser != null) {
                    val remoteAchievements = firebaseUser.unlockedAchievements ?: emptyList()
                    for (rId in remoteAchievements) {
                        try {
                            localRepository.triggerAchievement(rId)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        // Real-time continuous bi-directional synchronization of XP and coins
        viewModelScope.launch {
            kotlinx.coroutines.flow.combine(
                firebaseUserProfileStream,
                localUserProfileStream
            ) { firebaseUser, localUser ->
                Pair(firebaseUser, localUser)
            }.collect { (firebaseUser, localUser) ->
                if (firebaseUser != null && localUser != null) {
                    val firebaseXp = firebaseUser.xp ?: 0
                    val localXp = localUser.xp

                    // Logging for debug check
                    Log.d("NeoChem_Debug", "Sync Collection - Firestore XP: $firebaseXp, Local Room XP: $localXp, Leaderboard XP: $firebaseXp")

                    if (localXp != firebaseXp) {
                        val targetXp = maxOf(localXp, firebaseXp)
                        try {
                            if (localXp < targetXp) {
                                localRepository.earnXp(targetXp - localXp)
                            }
                            if (firebaseXp < targetXp) {
                                firebaseRepository.setAbsoluteXpAndLevel(targetXp)
                            }
                        } catch (e: Exception) {
                            Log.e("AuthViewModel", "Error in continuous XP sync: ${e.message}", e)
                        }
                    }

                    val firebaseCoins = firebaseUser.coins ?: firebaseUser.coin ?: 100
                    val localCoins = localUser.coins
                    if (localCoins != firebaseCoins) {
                        val targetCoins = maxOf(localCoins, firebaseCoins)
                        try {
                            if (localCoins < targetCoins) {
                                localRepository.earnCoins(targetCoins - localCoins)
                            }
                            if (firebaseCoins < targetCoins) {
                                firebaseRepository.setAbsoluteCoins(targetCoins)
                            }
                        } catch (e: Exception) {
                            Log.e("AuthViewModel", "Error in continuous coin sync: ${e.message}", e)
                        }
                    }
                }
            }
        }
    }

    fun register(email: String, password: String, username: String, avatarIndex: Int) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                firebaseRepository.registerUser(email, password, username, avatarIndex)
                _authState.value = AuthState.Success(email)
            } catch (e: Exception) {
                val errorMsg = e.message ?: ""
                val mappedError = if (errorMsg.contains("API key", ignoreCase = true) || errorMsg.contains("apikey", ignoreCase = true)) {
                    "API Key Firebase tidak valid. Harap masukkan API Key asli di panel Secrets AI Studio sebagai FIREBASE_API_KEY."
                } else {
                    errorMsg.ifEmpty { "Registrasi gagal." }
                }
                _authState.value = AuthState.Error(mappedError)
                Log.e("AuthViewModel", "Register error", e)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                firebaseRepository.loginUser(email, password)
                _authState.value = AuthState.Success(email)
            } catch (e: Exception) {
                val errorMsg = e.message ?: ""
                val mappedError = if (errorMsg.contains("API key", ignoreCase = true) || errorMsg.contains("apikey", ignoreCase = true)) {
                    "API Key Firebase tidak valid. Harap masukkan API Key asli di panel Secrets AI Studio sebagai FIREBASE_API_KEY."
                } else {
                    errorMsg.ifEmpty { "Masuk akun gagal." }
                }
                _authState.value = AuthState.Error(mappedError)
                Log.e("AuthViewModel", "Login error", e)
            }
        }
    }

    fun loginWithGoogleReal(context: android.content.Context, onError: (String) -> Unit) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val credentialManager = CredentialManager.create(context)
                
                val webClientId = try {
                    val resId = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
                    if (resId != 0) {
                        context.getString(resId)
                    } else {
                        throw Exception("Konfigurasi Firebase tidak valid: Web Client ID (default_web_client_id) tidak ditemukan. Daftarkan SHA-1 di Firebase Console & upload file google-services.json asli Anda.")
                    }
                } catch (e: Exception) {
                    if (e.message?.contains("Konfigurasi Firebase") == true) {
                        throw e
                    }
                    throw Exception("Konfigurasi Firebase tidak valid: Gagal memuat default_web_client_id. Periksa file google-services.json Anda.")
                }

                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(webClientId)
                    .setAutoSelectEnabled(true)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                
                handleGoogleSignInResult(result, onError)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Real CredentialManager error", e)
                val friendlyMessage = when {
                    e.message?.contains("Konfigurasi Firebase", ignoreCase = true) == true || e.message?.contains("default_web_client_id", ignoreCase = true) == true -> {
                        e.message ?: "Firebase Web Client ID tidak ditemukan. Periksa konfigurasi google-services.json Anda."
                    }
                    e.message?.contains("network", ignoreCase = true) == true -> {
                        "Gagal masuk. Periksa koneksi internet Anda."
                    }
                    e.message?.contains("No credentials", ignoreCase = true) == true || e.javaClass.simpleName.contains("NoCredentials", ignoreCase = true) -> {
                        "Gagal masuk: Tidak ada akun Google yang terdeteksi di perangkat Anda. Silakan tambahkan Google Account di pengaturan Android Anda terlebih dahulu."
                    }
                    e.message?.contains("canceled", ignoreCase = true) == true || e.javaClass.simpleName.contains("Cancellation", ignoreCase = true) -> {
                        "Proses masuk dibatalkan oleh pengguna."
                    }
                    else -> {
                        "Gagal masuk Google: ${e.localizedMessage ?: "Kesalahan SDK"}"
                    }
                }
                _authState.value = AuthState.Error(friendlyMessage)
                onError(friendlyMessage)
            }
        }
    }

    private suspend fun handleGoogleSignInResult(result: GetCredentialResponse, onError: (String) -> Unit) {
        try {
            val credential = result.credential
            if (credential is GoogleIdTokenCredential) {
                val idToken = credential.idToken
                val email = credential.id
                val name = credential.displayName ?: "Google User"
                val photoUrl = credential.profilePictureUri?.toString() ?: ""
                
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authInstance = firebaseRepository.auth ?: throw Exception("Firebase Auth tidak tersedia.")
                val authResult = authInstance.signInWithCredential(firebaseCredential).await()
                val user = authResult.user ?: throw Exception("Google Authentication Gagal: User Kosong.")
                
                val firestoreInstance = firebaseRepository.firestore ?: throw Exception("Cloud Firestore tidak tersedia.")
                val userDoc = firestoreInstance.collection("users").document(user.uid).get().await()
                if (!userDoc.exists()) {
                    val defaultUser = FirestoreUser(
                        uid = user.uid,
                        name = name,
                        username = name,
                        email = email,
                        photoUrl = photoUrl,
                        level = 1,
                        xp = 0,
                        totalQuiz = 0,
                        accuracy = 100,
                        coins = 100,
                        coin = 100,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        lastLogin = System.currentTimeMillis()
                    )
                    firestoreInstance.collection("users").document(user.uid).set(defaultUser).await()
                } else {
                    firestoreInstance.collection("users").document(user.uid)
                        .set(
                            mapOf(
                                "lastLogin" to System.currentTimeMillis(),
                                "updatedAt" to System.currentTimeMillis(),
                                "photoUrl" to photoUrl
                            ),
                            com.google.firebase.firestore.SetOptions.merge()
                        ).await()
                }

                localRepository.updateUsernameAndAvatar(name, 0, photoUrl)
                _authState.value = AuthState.Success(email)
            } else {
                throw Exception("Sign in gagal. Silakan coba lagi.")
            }
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Failure syncing Google Login credential", e)
            _authState.value = AuthState.Error("Gagal masuk. Periksa koneksi internet Anda.")
            onError("Gagal masuk. Periksa koneksi internet Anda.")
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                firebaseRepository.sendPasswordResetEmail(email)
                _authState.value = AuthState.Success("Reset email terkirim.")
            } catch (e: Exception) {
                val errorMsg = e.message ?: ""
                val mappedError = if (errorMsg.contains("API key", ignoreCase = true) || errorMsg.contains("apikey", ignoreCase = true)) {
                    "API Key Firebase tidak valid. Harap masukkan API Key asli di panel Secrets AI Studio sebagai FIREBASE_API_KEY."
                } else {
                    errorMsg.ifEmpty { "Reset password gagal." }
                }
                _authState.value = AuthState.Error(mappedError)
            }
        }
    }

    fun signOut() {
        firebaseRepository.logout()
        _authState.value = AuthState.Idle
    }

    fun updateProfile(name: String, avatarIdx: Int) {
        viewModelScope.launch {
            val currentPhoto = userProfile.value?.photoUrl ?: ""
            // Update local Room database
            localRepository.updateUsernameAndAvatar(name, avatarIdx, currentPhoto)

            // Dynamic Firestore sync
            val current = firebaseRepository.getCurrentUser()
            if (current != null) {
                try {
                    val finalPhoto = if (currentPhoto.startsWith("http")) currentPhoto else "avatar_$avatarIdx"
                    firebaseRepository.updateProfile(name, finalPhoto)
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "Firestore sync error", e)
                }
            }
        }
    }

    fun uploadProfileImageUri(uri: Uri) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val downloadUrl = firebaseRepository.uploadCompressedProfileImage(uri)
                // Extract or parse name for storage
                val currentProfile = userProfile.value
                val user = firebaseRepository.getCurrentUser()
                if (user != null && currentProfile != null) {
                    firebaseRepository.updateProfile(currentProfile.username, downloadUrl)
                }
                if (currentProfile != null) {
                    localRepository.updateUsernameAndAvatar(currentProfile.username, currentProfile.avatarIndex, downloadUrl)
                }
                _authState.value = AuthState.Success("Upload berhasil")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Upload gagal.")
            }
        }
    }

    fun clearProfileImage() {
        viewModelScope.launch {
            val currentProfile = userProfile.value ?: return@launch
            // Reset locally
            localRepository.updateUsernameAndAvatar(currentProfile.username, currentProfile.avatarIndex, "")

            // Sync with Firestore
            val current = firebaseRepository.getCurrentUser()
            if (current != null) {
                try {
                    firebaseRepository.updateProfile(currentProfile.username, "avatar_${currentProfile.avatarIndex}")
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "Firestore clear photo sync error", e)
                }
            }
        }
    }

    fun claimDailyReward() {
        viewModelScope.launch {
            localRepository.claimDailyReward()
            val current = firebaseRepository.getCurrentUser()
            if (current != null) {
                try {
                    firebaseRepository.gainXpAndAdjustLevel(150)
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "Daily reward sync failing", e)
                }
            }
        }
    }

    fun earnXp(amount: Int, quizAccuracy: Int = -1) {
        viewModelScope.launch {
            localRepository.earnXp(amount)
            val current = firebaseRepository.getCurrentUser()
            if (current != null) {
                try {
                    firebaseRepository.gainXpAndAdjustLevel(amount, quizAccuracy)
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "XP Fire sync failing", e)
                }
            }
        }
    }

    fun triggerAchievement(id: String) {
        viewModelScope.launch {
            val newlyTriggered = localRepository.triggerAchievement(id)
            if (newlyTriggered && firebaseRepository.getCurrentUser() != null) {
                try {
                    firebaseRepository.unlockAchievement(id)
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "Failed syncing unlocked achievement to Firestore: ${e.message}", e)
                }
            }
        }
    }

    fun completeSimulation(topic: String = "general") {
        viewModelScope.launch {
            localRepository.completeSimulation()
            val finalTopic = topic.lowercase().trim()
            if (finalTopic == "hydrocarbon") {
                triggerAchievement("badge_alkana")
            } else if (finalTopic == "reactor") {
                triggerAchievement("badge_korosi")
            } else if (finalTopic == "atom") {
                triggerAchievement("badge_master_atom")
            } else if (finalTopic == "speedster") {
                triggerAchievement("badge_speedster")
            }
            if (firebaseRepository.getCurrentUser() != null) {
                try {
                    firebaseRepository.gainXpAndAdjustLevel(40)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Flashcard system integration
    val allFlashcardStatus: StateFlow<List<com.example.data.model.FlashcardStatus>> = localRepository.allFlashcardStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateFlashcardStatus(cardId: String, isDifficult: Boolean, isFavorite: Boolean) {
        viewModelScope.launch {
            localRepository.updateFlashcardStatus(cardId, isDifficult, isFavorite)
        }
    }

    private val _coinEarnedFlow = MutableSharedFlow<Int>(extraBufferCapacity = 5)
    val coinEarnedFlow: SharedFlow<Int> = _coinEarnedFlow.asSharedFlow()

    fun earnCoins(amount: Int) {
        viewModelScope.launch {
            localRepository.earnCoins(amount)
        }
    }

    fun spendCoins(amount: Int, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val success = localRepository.spendCoins(amount)
            onResult(success)
        }
    }

    fun completeReadingMaterial(materiId: String) {
        viewModelScope.launch {
            val isNewlyCompleted = localRepository.completeReadingMaterial(materiId)
            if (isNewlyCompleted && firebaseRepository.getCurrentUser() != null) {
                try {
                    firebaseRepository.gainXpAndAdjustLevel(20)
                    firebaseRepository.completeMaterial(materiId)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Mystery Box reward grant
    fun claimMysteryBoxReward(coinsAwarded: Int, xpAwarded: Int, completionCallback: () -> Unit = {}) {
        viewModelScope.launch {
            if (xpAwarded > 0) {
                localRepository.earnXp(xpAwarded)
            }
            completionCallback()
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            localRepository.resetData()
        }
    }
}
