package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.model.UserProfile
import com.example.data.model.UserAchievement
import com.example.data.model.ElementFavorite
import com.example.data.model.MateriProgress
import com.example.data.model.FlashcardStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(private val appDao: AppDao) {

    companion object {
        val coinEventFlow = kotlinx.coroutines.flow.MutableSharedFlow<Int>(extraBufferCapacity = 10)
    }

    val userProfile: Flow<UserProfile?> = appDao.getUserProfile()
    val allAchievements: Flow<List<UserAchievement>> = appDao.getAllAchievements()
    val favoriteElements: Flow<List<ElementFavorite>> = appDao.getFavoriteElements()
    val allMateriProgress: Flow<List<MateriProgress>> = appDao.getAllMateriProgress()
    val allFlashcardStatus: Flow<List<FlashcardStatus>> = appDao.getAllFlashcardStatus()

    suspend fun updateFlashcardStatus(cardId: String, isDifficult: Boolean, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        val status = FlashcardStatus(cardId = cardId, isDifficult = isDifficult, isFavorite = isFavorite)
        appDao.insertFlashcardStatus(status)
    }

    init {
        // Initialize default user profile and achievements asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Check if user exists
                val currentProfile = appDao.getUserProfileOnce()
                if (currentProfile == null) {
                    val defaultProfile = UserProfile(
                        id = 1,
                        username = "SiswaNeo",
                        email = "belajar@neochem.edu",
                        xp = 0,
                        level = 1,
                        lastLoginTimestamp = System.currentTimeMillis(),
                        coins = 100
                    )
                    appDao.insertProfile(defaultProfile)
                }

                // Initial Achievements setup
                val existingAchievements = appDao.getAllAchievementsOnce()
                // Overwrite list if empty or if any old ID schema exists to self-heal cleanly
                if (existingAchievements.isEmpty() || !existingAchievements.all { it.id.startsWith("badge_") }) {
                    // Pre-clear old achievements if any existed, keeping schema pristine
                    val defaultAchievements = listOf(
                        UserAchievement(
                            id = "badge_welcome",
                            title = "Siswa Baru NeoChem",
                            description = "Selamat datang! Silakan klaim reward login harian pertamamu untuk melangkah.",
                            iconName = "handshake",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_master_atom",
                            title = "Master Struktur Atom",
                            description = "Menyelesaikan simulasi konfigurasi mekanika struktur atom modern.",
                            iconName = "science",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_korosi",
                            title = "Spesialis Anti-Korosi",
                            description = "Menjelajahi visualisasi deret sel volta dan perkaratan logam.",
                            iconName = "build",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_alkana",
                            title = "Suhu Hidrokarbon Alkana",
                            description = "Menyusun rantai karbon organik dalam laboratorium simulasi.",
                            iconName = "hub",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_perfect",
                            title = "Akurasi Sempurna (100%)",
                            description = "Memperoleh skor bulat sempurna 100% pada pengerjaan Quiz.",
                            iconName = "star",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_kimia_pro",
                            title = "Ahli Kimia Profesional",
                            description = "Tekun belajar hingga mencapai Level 5 pada track akademik.",
                            iconName = "workspace_premium",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_element_hunter",
                            title = "Kolektor Unsur Favorit",
                            description = "Mengidentifikasi & menandai 3 unsur penting pada Tabel Periodik.",
                            iconName = "favorite",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_reaksi_master",
                            title = "Pakar Stoikiometri",
                            description = "Mempelajari perhitungan hukum kekekalan materi dan reaksi kimia.",
                            iconName = "auto_stories",
                            isEarned = false
                        ),
                        UserAchievement(
                            id = "badge_speedster",
                            title = "Reaksi Kilat Katalis",
                            description = "Menyelesaikan simulasi reaktor lab dengan multiplier kencang.",
                            iconName = "directions_run",
                            isEarned = false
                        )
                    )
                    appDao.insertAchievements(defaultAchievements)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun resetData() = withContext(Dispatchers.IO) {
        val defaultProfile = UserProfile(
            id = 1,
            username = "SiswaNeo",
            email = "belajar@neochem.edu",
            xp = 0,
            level = 1,
            lastLoginTimestamp = System.currentTimeMillis(),
            coins = 100
        )
        appDao.insertProfile(defaultProfile)
        
        // Reset achievements
        val achievements = appDao.getAllAchievementsOnce()
        val resetList = achievements.map { it.copy(isEarned = false, earnedTimestamp = 0L) }
        appDao.insertAchievements(resetList)
    }

    suspend fun updateUsernameAndAvatar(name: String, avatarIdx: Int, photoUrl: String = "") = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext
        val updated = currentProfile.copy(username = name, avatarIndex = avatarIdx, photoUrl = photoUrl)
        appDao.updateProfile(updated)
    }

    suspend fun earnXp(amount: Int): Unit = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext
        val oldLevel = currentProfile.level
        val totalXp = currentProfile.xp + amount
        val newLevel = com.example.util.LevelSystem.calculateLevel(totalXp)
        
        var addedCoins = 0

        val updated = currentProfile.copy(
            xp = totalXp, 
            level = newLevel,
            coins = currentProfile.coins + addedCoins
        )
        appDao.updateProfile(updated)

        if (addedCoins > 0) {
            coinEventFlow.emit(addedCoins)
        }

        // Check level 5 achievement
        if (newLevel >= 5) {
            triggerAchievement("badge_kimia_pro")
        }
    }

    suspend fun triggerAchievement(id: String): Boolean = withContext(Dispatchers.IO) {
        val achievements = appDao.getAllAchievementsOnce()
        val found = achievements.find { it.id == id } ?: return@withContext false
        if (!found.isEarned) {
            val updated = found.copy(isEarned = true, earnedTimestamp = System.currentTimeMillis())
            appDao.updateAchievement(updated)
            // also award a nice 100 XP bonus!
            earnXp(100)
            true
        } else {
            false
        }
    }

    suspend fun claimDailyReward() = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext
        val now = System.currentTimeMillis()
        
        val updated = currentProfile.copy(lastLoginTimestamp = now)
        appDao.updateProfile(updated)
        
        // Reward 150 XP
        earnXp(150)
        triggerAchievement("badge_welcome")
    }

    suspend fun toggleFavoriteElement(atomicNumber: Int) = withContext(Dispatchers.IO) {
        val currentFavs = appDao.getFavoriteElementsOnce()
        val exists = currentFavs.any { it.atomicNumber == atomicNumber }
        if (exists) {
            appDao.deleteFavoriteElement(atomicNumber)
        } else {
            appDao.insertFavoriteElement(ElementFavorite(atomicNumber, true))
            
            // Re-read favorite items to verify achievement trigger
            val updatedFavs = appDao.getFavoriteElementsOnce()
            if (updatedFavs.size >= 3) {
                triggerAchievement("badge_element_hunter")
            }
        }
    }

    suspend fun completeMateri(materiId: String, score: Int) = withContext(Dispatchers.IO) {
        val currProgressList = appDao.getAllMateriProgressOnce()
        val curr = currProgressList.find { it.materiId == materiId }
        val newScore = maxOf(curr?.highScore ?: 0, score)
        val progress = MateriProgress(materiId = materiId, isCompleted = true, highScore = newScore)
        appDao.insertMateriProgress(progress)

        // Give XP reward
        earnXp(50)

        // Trigger reactions/perfection achievements
        if (score == 100) {
            triggerAchievement("badge_perfect")
        }
        if (materiId == "stoikiometri" || materiId == "redoks") {
            triggerAchievement("badge_reaksi_master")
        }
    }

    suspend fun completeReadingMaterial(materiId: String): Boolean = withContext(Dispatchers.IO) {
        val currProgressList = appDao.getAllMateriProgressOnce()
        val curr = currProgressList.find { it.materiId == materiId }
        if (curr == null || !curr.isCompleted) {
            val progress = MateriProgress(materiId = materiId, isCompleted = true, highScore = maxOf(curr?.highScore ?: 0, 0))
            appDao.insertMateriProgress(progress)
            
            earnXp(20)
            true
        } else {
            false
        }
    }

    suspend fun earnCoins(amount: Int) = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext
        val updated = currentProfile.copy(coins = currentProfile.coins + amount)
        appDao.updateProfile(updated)
        coinEventFlow.emit(amount)
    }

    suspend fun spendCoins(amount: Int): Boolean = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext false
        if (currentProfile.coins < amount) return@withContext false
        val updated = currentProfile.copy(coins = currentProfile.coins - amount)
        appDao.updateProfile(updated)
        coinEventFlow.emit(-amount)
        true
    }

    suspend fun completeSimulation() = withContext(Dispatchers.IO) {
        val currentProfile = appDao.getUserProfileOnce() ?: return@withContext
        val updated = currentProfile.copy(
            totalSimulationsCompleted = currentProfile.totalSimulationsCompleted + 1
        )
        appDao.updateProfile(updated)
        earnXp(40)
    }

    suspend fun getUserProfileOnce(): UserProfile? = withContext(Dispatchers.IO) {
        appDao.getUserProfileOnce()
    }
}
