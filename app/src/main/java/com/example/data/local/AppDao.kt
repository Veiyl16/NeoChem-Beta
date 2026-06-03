package com.example.data.local

import androidx.room.*
import com.example.data.model.UserProfile
import com.example.data.model.UserAchievement
import com.example.data.model.ElementFavorite
import com.example.data.model.MateriProgress
import com.example.data.model.FlashcardStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // User Profile
    @Query("SELECT * FROM user_profiles WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profiles WHERE id = 1")
    suspend fun getUserProfileOnce(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfile)

    @Update
    suspend fun updateProfile(profile: UserProfile)

    // User Achievements
    @Query("SELECT * FROM user_achievements")
    fun getAllAchievements(): Flow<List<UserAchievement>>

    @Query("SELECT * FROM user_achievements")
    suspend fun getAllAchievementsOnce(): List<UserAchievement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<UserAchievement>)

    @Update
    suspend fun updateAchievement(achievement: UserAchievement)

    // Element Favorites
    @Query("SELECT * FROM element_favorites")
    fun getFavoriteElements(): Flow<List<ElementFavorite>>

    @Query("SELECT * FROM element_favorites")
    suspend fun getFavoriteElementsOnce(): List<ElementFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteElement(fav: ElementFavorite)

    @Query("DELETE FROM element_favorites WHERE atomicNumber = :atomicNumber")
    suspend fun deleteFavoriteElement(atomicNumber: Int)

    // Materi Progress
    @Query("SELECT * FROM materi_progress")
    fun getAllMateriProgress(): Flow<List<MateriProgress>>

    @Query("SELECT * FROM materi_progress")
    suspend fun getAllMateriProgressOnce(): List<MateriProgress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMateriProgress(progress: MateriProgress)

    // Flashcard Status
    @Query("SELECT * FROM flashcard_status")
    fun getAllFlashcardStatus(): Flow<List<FlashcardStatus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcardStatus(status: FlashcardStatus)
}
