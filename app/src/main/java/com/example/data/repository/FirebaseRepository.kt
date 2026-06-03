package com.example.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.data.dummy.ChemistryData
import com.example.data.dummy.PeriodicTableData
import com.example.data.model.ChemicalElement
import com.example.data.model.MateriCategory
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

// Reliable User Data Model for Firestore mapping
data class FirestoreUser(
    val uid: String? = "",
    val name: String? = "",
    val username: String? = "SiswaNeo",
    val email: String? = "",
    val photoUrl: String? = "",
    val level: Int? = 1,
    val xp: Int? = 0,
    val coin: Int? = 100,
    val coins: Int? = 100,
    val totalQuiz: Int? = 0,
    val accuracy: Int? = 0,
    val createdAt: Long? = 0L,
    val lastLogin: Long? = 0L,
    val updatedAt: Long? = 0L,
    val unlockedAchievements: List<String>? = emptyList()
)

data class FirestoreProgress(
    val uid: String? = "",
    val completedMaterials: List<String>? = emptyList(),
    val completedQuiz: List<String>? = emptyList(),
    val unlockedLevels: List<Int>? = listOf(1),
    val savedElements: List<Int>? = emptyList(),
    val lastStudy: Long? = 0L,
    val favoriteTopics: List<String>? = emptyList()
)

data class FirestoreLeaderboardEntry(
    val uid: String? = "",
    val username: String? = "",
    val level: Int? = 1,
    val xp: Int? = 0,
    val accuracy: Int? = 0,
    val photoUrl: String? = ""
)

class FirebaseRepository(private val context: Context) {

    val auth: FirebaseAuth? by lazy {
        try {
            initFirebaseIfNeeded()
            FirebaseAuth.getInstance()
        } catch (e: Throwable) {
            Log.e("FirebaseRepository", "FirebaseAuth.getInstance() failed: ${e.message}", e)
            null
        }
    }

    val firestore: FirebaseFirestore? by lazy {
        try {
            initFirebaseIfNeeded()
            val db = FirebaseFirestore.getInstance()
            // Enable Offline Persistence explicitly
            try {
                val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
                db.firestoreSettings = settings
            } catch (e: Throwable) {
                Log.e("FirebaseRepository", "Persistence already initialized or error: ${e.message}")
            }
            db
        } catch (e: Throwable) {
            Log.e("FirebaseRepository", "FirebaseFirestore.getInstance() failed: ${e.message}", e)
            null
        }
    }

    private val storage: FirebaseStorage? by lazy {
        try {
            initFirebaseIfNeeded()
            FirebaseStorage.getInstance()
        } catch (e: Throwable) {
            Log.e("FirebaseRepository", "FirebaseStorage.getInstance() failed: ${e.message}", e)
            null
        }
    }

    private fun initFirebaseIfNeeded() {
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                val apiKeyVal = try {
                    com.example.BuildConfig.FIREBASE_API_KEY
                } catch (e: Throwable) {
                    "MockApiKeyValueThatCompilesAndRunsPerfect"
                }
                val appIdVal = try {
                    com.example.BuildConfig.FIREBASE_APPLICATION_ID
                } catch (e: Throwable) {
                    "1:536838337190:android:d2a9f1e01c1540dc"
                }
                val projectIdVal = try {
                    com.example.BuildConfig.FIREBASE_PROJECT_ID
                } catch (e: Throwable) {
                    "neochem-d2a9f"
                }
                val bucketVal = try {
                    com.example.BuildConfig.FIREBASE_STORAGE_BUCKET
                } catch (e: Throwable) {
                    "neochem-d2a9f.appspot.com"
                }

                val useApiKey = if (apiKeyVal.isNullOrBlank() || apiKeyVal == "MY_FIREBASE_API_KEY") "MockApiKeyValueThatCompilesAndRunsPerfect" else apiKeyVal
                val useAppId = if (appIdVal.isNullOrBlank() || appIdVal == "MY_FIREBASE_APPLICATION_ID") "1:536838337190:android:d2a9f1e01c1540dc" else appIdVal
                val useProjectId = if (projectIdVal.isNullOrBlank() || projectIdVal == "MY_FIREBASE_PROJECT_ID") "neochem-d2a9f" else projectIdVal
                val useBucket = if (bucketVal.isNullOrBlank() || bucketVal == "MY_FIREBASE_STORAGE_BUCKET") "neochem-d2a9f.appspot.com" else bucketVal

                val options = FirebaseOptions.Builder()
                    .setApplicationId(useAppId)
                    .setApiKey(useApiKey)
                    .setProjectId(useProjectId)
                    .setStorageBucket(useBucket)
                    .build()
                FirebaseApp.initializeApp(context, options)
                Log.d("FirebaseRepository", "Firebase programmatic initialization successful.")
            }
        } catch (e: Throwable) {
            Log.e("FirebaseRepository", "Firebase programmatic initialization failed: ${e.message}", e)
        }
    }

    // AUTH ACTIONS
    fun getCurrentUser(): FirebaseUser? {
        return auth?.currentUser
    }

    // Flow that emits current Firestore user profile dynamically
    fun streamUserProfile(): Flow<FirestoreUser?> = callbackFlow {
        val authInstance = auth
        val firestoreInstance = firestore
        if (authInstance == null || firestoreInstance == null) {
            trySend(null)
            close()
            return@callbackFlow
        }
        val currentUser = authInstance.currentUser
        if (currentUser == null) {
            trySend(null)
            close()
            return@callbackFlow
        }

        val listener = firestoreInstance.collection("users")
            .document(currentUser.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirebaseRepository", "Listen failed: $error")
                    trySend(null)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    try {
                        val user = snapshot.toObject(FirestoreUser::class.java)?.copy(uid = snapshot.id)
                        trySend(user)
                    } catch (e: Exception) {
                        Log.e("FirebaseRepository", "Error parsing FirestoreUser mapping safely: ${e.message}", e)
                        // Fallback manual construction to prevent crash
                        val fallback = FirestoreUser(
                            uid = snapshot.id,
                            name = snapshot.getString("name") ?: snapshot.getString("username") ?: "Pengguna",
                            username = snapshot.getString("username") ?: "Pengguna",
                            email = snapshot.getString("email") ?: "",
                            photoUrl = snapshot.getString("photoUrl") ?: "",
                            level = (snapshot.get("level") as? Number)?.toInt() ?: 1,
                            xp = (snapshot.get("xp") as? Number)?.toInt() ?: 0,
                            coin = (snapshot.get("coin") as? Number)?.toInt() ?: 100,
                            coins = (snapshot.get("coins") as? Number)?.toInt() ?: 100,
                            totalQuiz = (snapshot.get("totalQuiz") as? Number)?.toInt() ?: 0
                        )
                        trySend(fallback)
                    }
                } else {
                    trySend(null)
                }
            }
        awaitClose { listener.remove() }
    }

    // Flow that streams current user progress
    fun streamUserProgress(): Flow<FirestoreProgress?> = callbackFlow {
        val authInstance = auth
        val firestoreInstance = firestore
        if (authInstance == null || firestoreInstance == null) {
            trySend(null)
            close()
            return@callbackFlow
        }
        val currentUser = authInstance.currentUser
        if (currentUser == null) {
            trySend(null)
            close()
            return@callbackFlow
        }

        val listener = firestoreInstance.collection("user_progress")
            .document(currentUser.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirebaseRepository", "Listen progress failed: $error")
                    trySend(null)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    try {
                        val progress = snapshot.toObject(FirestoreProgress::class.java)?.copy(uid = snapshot.id)
                        trySend(progress)
                    } catch (e: Exception) {
                        Log.e("FirebaseRepository", "Error reading progress snapshot safe-catch: ${e.message}", e)
                        trySend(FirestoreProgress(uid = snapshot.id))
                    }
                } else {
                    trySend(null)
                }
            }
        awaitClose { listener.remove() }
    }

    // Sign up with Firestore database automatic initialization
    suspend fun registerUser(email: String, password: String, username: String, defaultAvatarIndex: Int): FirebaseUser = withContext(Dispatchers.IO) {
        val authInstance = auth ?: throw Exception("Firebase Auth tidak tersedia.")
        val firestoreInstance = firestore ?: throw Exception("Cloud Firestore tidak tersedia.")
        
        val authResult = authInstance.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user ?: throw Exception("Pendaftaran gagal: User kosong.")
        
        // Initialize user record
        val defaultUser = FirestoreUser(
            uid = user.uid,
            name = username,
            username = username,
            email = email,
            photoUrl = "avatar_$defaultAvatarIndex",
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

        // Initialize progress record
        val progress = FirestoreProgress(
            uid = user.uid,
            unlockedLevels = listOf(1)
        )
        firestoreInstance.collection("user_progress").document(user.uid).set(progress).await()

        // Seed references if needed
        seedDatabaseIfNeeded()

        user
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser = withContext(Dispatchers.IO) {
        val authInstance = auth ?: throw Exception("Firebase Auth tidak tersedia.")
        val firestoreInstance = firestore ?: throw Exception("Cloud Firestore tidak tersedia.")

        val authResult = authInstance.signInWithEmailAndPassword(email, password).await()
        val user = authResult.user ?: throw Exception("Masuk gagal: User kosong.")
        
        // Ensure user collection has the user, and if not, we create with full defaults to prevent NOT_FOUND / empty state
        val userDoc = firestoreInstance.collection("users").document(user.uid).get().await()
        if (!userDoc.exists()) {
            val defaultUser = FirestoreUser(
                uid = user.uid,
                name = user.displayName ?: email.substringBefore("@"),
                username = user.displayName ?: email.substringBefore("@"),
                email = email,
                photoUrl = "avatar_0",
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
            // Document exists, update safely using set with merge to avoid update NOT_FOUND failures
            firestoreInstance.collection("users").document(user.uid)
                .set(
                    mapOf(
                        "lastLogin" to System.currentTimeMillis(),
                        "updatedAt" to System.currentTimeMillis()
                    ),
                    com.google.firebase.firestore.SetOptions.merge()
                ).await()
        }

        // Also check and ensure user_progress document exists during login to prevent further sync transaction errors
        val progressDoc = firestoreInstance.collection("user_progress").document(user.uid).get().await()
        if (!progressDoc.exists()) {
            val progress = FirestoreProgress(
                uid = user.uid,
                unlockedLevels = listOf(1)
            )
            firestoreInstance.collection("user_progress").document(user.uid).set(progress).await()
        }

        // Seed references if needed
        seedDatabaseIfNeeded()

        user
    }

    suspend fun sendPasswordResetEmail(email: String) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: throw Exception("Firebase Auth tidak tersedia.")
        authInstance.sendPasswordResetEmail(email).await()
    }

    fun logout() {
        auth?.signOut()
    }

    // XP & LEVELING SYSTEM (With reliable transaction logic and increment)
    suspend fun gainXpAndAdjustLevel(xpGained: Int, quizAccuracy: Int = -1) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)

        firestoreInstance.runTransaction { transaction ->
            val snapshot = transaction.get(userDocRef)
            val currentXp = snapshot.getLong("xp")?.toInt() ?: 0
            val totalQuizCount = snapshot.getLong("totalQuiz")?.toInt() ?: 0
            val currentAccuracy = snapshot.getLong("accuracy")?.toInt() ?: 0

            val newXp = currentXp + xpGained
            val newLevel = com.example.util.LevelSystem.calculateLevel(newXp)

            val updates = mutableMapOf<String, Any>(
                "xp" to newXp,
                "level" to newLevel
            )

            if (quizAccuracy >= 0) {
                val newTotalQuizzes = totalQuizCount + 1
                val calculatedAccuracy = if (totalQuizCount == 0) quizAccuracy else {
                    ((currentAccuracy * totalQuizCount) + quizAccuracy) / newTotalQuizzes
                }
                updates["totalQuiz"] = newTotalQuizzes
                updates["accuracy"] = calculatedAccuracy
            }

            if (!snapshot.exists()) {
                // Populate default fields to ensure user profiles are automatically initialized on set with merge if missing
                updates["uid"] = currentUser.uid
                updates["username"] = currentUser.displayName ?: currentUser.email?.substringBefore("@") ?: "NeoLearner"
                updates["email"] = currentUser.email ?: ""
                updates["photoUrl"] = "avatar_0"
                updates["coins"] = 100
            }

            // Using set with merge is fully equivalent to update, but is incredibly resilient and automatically creates document if missing
            transaction.set(userDocRef, updates, com.google.firebase.firestore.SetOptions.merge())
        }.await()
    }

    // LEADERBOARD QUERY FLOWS
    fun getLeaderboardFlow(): Flow<List<FirestoreLeaderboardEntry>> = callbackFlow {
        val firestoreInstance = firestore
        if (firestoreInstance == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }
        val listener = firestoreInstance.collection("users")
            .orderBy("xp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(20)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirebaseRepository", "Listen leaderboard on users failed: $error")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        val entryXp = doc.getLong("xp")?.toInt() ?: 0
                        Log.d("NeoChem_Debug", "Leaderboard Entry XP: $entryXp for user ${doc.getString("username")}")
                        FirestoreLeaderboardEntry(
                            uid = doc.id,
                            username = doc.getString("username") ?: doc.getString("name") ?: "SiswaNeo",
                            level = doc.getLong("level")?.toInt() ?: 1,
                            xp = entryXp,
                            accuracy = doc.getLong("accuracy")?.toInt() ?: 100,
                            photoUrl = doc.getString("photoUrl") ?: ""
                        )
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    // PROFILE EDIT (Update username / edit picture uploading and compressing to Firebase Storage)
    suspend fun updateProfile(username: String, avatarUrlOrIndex: String) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)

        // Using set with merge to guarantee document is automatically created if missing, avoiding NOT_FOUND
        userDocRef.set(
            mapOf(
                "username" to username,
                "name" to username,
                "photoUrl" to avatarUrlOrIndex,
                "updatedAt" to System.currentTimeMillis()
            ),
            com.google.firebase.firestore.SetOptions.merge()
        ).await()
    }

    // Core compressed image upload to storage
    suspend fun uploadCompressedProfileImage(imageUri: Uri): String = withContext(Dispatchers.IO) {
        val authInstance = auth ?: throw Exception("Belum login.")
        val firestoreInstance = firestore ?: throw Exception("Firestore tidak tersedia.")
        val storageInstance = storage ?: throw Exception("Storage tidak tersedia.")
        
        val currentUser = authInstance.currentUser ?: throw Exception("Belum login.")
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream) ?: throw Exception("Gambar tidak dapat dimuat.")

        // Compress image to light JPEG payload
        val byteArrayOutputStream = ByteArrayOutputStream()
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
        val compressedBytes = byteArrayOutputStream.toByteArray()

        val storagePathRef = storageInstance.reference.child("profiles/${currentUser.uid}_${UUID.randomUUID()}.jpg")
        val uploadTask = storagePathRef.putBytes(compressedBytes).await()
        val downloadUrl = storagePathRef.downloadUrl.await().toString()

        // Sync inside user doc directly
        updateProfile(
            username = firestoreInstance.collection("users").document(currentUser.uid).get().await().getString("username") ?: "NeoLearner",
            avatarUrlOrIndex = downloadUrl
        )

        downloadUrl
    }

    // SYNC ELEMENTS PREFERENCES
    suspend fun toggleFavoriteElement(atomicNumber: Int) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val progressRef = firestoreInstance.collection("user_progress").document(currentUser.uid)

        firestoreInstance.runTransaction { transaction ->
            val snapshot = transaction.get(progressRef)
            val currentCompleted = snapshot.toObject(FirestoreProgress::class.java) ?: FirestoreProgress()
            val list = (currentCompleted.savedElements ?: emptyList()).toMutableList()
            if (list.contains(atomicNumber)) {
                list.remove(atomicNumber)
            } else {
                list.add(atomicNumber)
            }
            val updates = mapOf("savedElements" to list)
            // Using set with merge so it always self-heals if document was missing
            transaction.set(progressRef, updates, com.google.firebase.firestore.SetOptions.merge())
        }.await()
    }

    // SAVE MATERIALS LEARNING
    suspend fun completeMaterial(materialId: String) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val progressRef = firestoreInstance.collection("user_progress").document(currentUser.uid)

        firestoreInstance.runTransaction { transaction ->
            val snapshot = transaction.get(progressRef)
            val baseProgress = snapshot.toObject(FirestoreProgress::class.java) ?: FirestoreProgress()
            val materials = (baseProgress.completedMaterials ?: emptyList()).toMutableList()
            if (!materials.contains(materialId)) {
                materials.add(materialId)
            }
            val updates = mapOf("completedMaterials" to materials)
            // Using set with merge so it always self-heals if document was missing
            transaction.set(progressRef, updates, com.google.firebase.firestore.SetOptions.merge())
        }.await()
    }

    suspend fun unlockAchievement(achievementId: String) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)

        try {
            firestoreInstance.runTransaction { transaction ->
                val snapshot = transaction.get(userDocRef)
                val currentList = (snapshot.get("unlockedAchievements") as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()
                if (!currentList.contains(achievementId)) {
                    val updatedList = currentList + achievementId
                    val updates = mapOf(
                        "unlockedAchievements" to updatedList
                    )
                    transaction.set(userDocRef, updates, com.google.firebase.firestore.SetOptions.merge())
                }
            }.await()

            // Unlocking achievement gives +100 XP (no coins)
            gainXpAndAdjustLevel(100)
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error unlocking achievement $achievementId on Firestore: ${e.message}", e)
        }
    }

    suspend fun adjustCoins(amount: Int) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)
        firestoreInstance.runTransaction { transaction ->
            val snapshot = transaction.get(userDocRef)
            val currentCoins = snapshot.getLong("coins")?.toInt() ?: snapshot.getLong("coin")?.toInt() ?: 100
            val updates = mutableMapOf<String, Any>(
                "coins" to currentCoins + amount,
                "coin" to currentCoins + amount
            )
            if (!snapshot.exists()) {
                updates["uid"] = currentUser.uid
                updates["username"] = currentUser.displayName ?: currentUser.email?.substringBefore("@") ?: "NeoLearner"
                updates["email"] = currentUser.email ?: ""
                updates["photoUrl"] = "avatar_0"
                updates["xp"] = 0
                updates["level"] = 1
            }
            // Using set with merge so it always self-heals if document was missing
            transaction.set(userDocRef, updates, com.google.firebase.firestore.SetOptions.merge())
        }.await()
    }

    suspend fun setAbsoluteXpAndLevel(absoluteXp: Int) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)

        val finalLevel = com.example.util.LevelSystem.calculateLevel(absoluteXp)

        val updates = mapOf(
            "xp" to absoluteXp,
            "level" to finalLevel,
            "updatedAt" to System.currentTimeMillis()
        )
        firestoreInstance.collection("users").document(currentUser.uid)
            .set(updates, com.google.firebase.firestore.SetOptions.merge())
            .await()
    }

    suspend fun setAbsoluteCoins(absoluteCoins: Int) = withContext(Dispatchers.IO) {
        val authInstance = auth ?: return@withContext
        val firestoreInstance = firestore ?: return@withContext
        val currentUser = authInstance.currentUser ?: return@withContext
        val userDocRef = firestoreInstance.collection("users").document(currentUser.uid)

        val updates = mapOf(
            "coins" to absoluteCoins,
            "coin" to absoluteCoins,
            "updatedAt" to System.currentTimeMillis()
        )
        firestoreInstance.collection("users").document(currentUser.uid)
            .set(updates, com.google.firebase.firestore.SetOptions.merge())
            .await()
    }

    // SEED DATABASE METADATA IF NOT EXISTENT
    private suspend fun seedDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        val firestoreInstance = firestore ?: return@withContext
        // Materials check and seed
        val countMaterials = firestoreInstance.collection("materials").limit(1).get().await().size()
        if (countMaterials == 0) {
            ChemistryData.categories.forEach { cat ->
                val matMap = mapOf(
                    "title" to cat.title,
                    "category" to cat.id,
                    "difficulty" to cat.difficulty,
                    "xpReward" to 30,
                    "content" to cat.subMateriList.joinToString("\n\n") { it.fullText }
                )
                firestoreInstance.collection("materials").document(cat.id).set(matMap)
            }
        }

        // Periodic elements check and seed
        val countElements = firestoreInstance.collection("periodic_elements").limit(1).get().await().size()
        if (countElements == 0) {
            PeriodicTableData.elements.forEach { ele ->
                val eleMap = mapOf(
                    "name" to ele.name,
                    "symbol" to ele.symbol,
                    "atomicNumber" to ele.number,
                    "atomicMass" to ele.mass,
                    "electronConfig" to ele.electronConfig,
                    "category" to ele.category,
                    "phase" to ele.phase,
                    "period" to ele.period,
                    "group" to ele.group,
                    "description" to ele.uses,
                    "uses" to ele.uses,
                    "funFact" to ele.interestingFact
                )
                firestoreInstance.collection("periodic_elements").document(ele.symbol).set(eleMap)
            }
        }
    }
}
