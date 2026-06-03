package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val username: String = "NeoLearner",
    val email: String = "neolearner@neochem.edu",
    val xp: Int = 0,
    val level: Int = 1,
    val lastLoginTimestamp: Long = System.currentTimeMillis(),
    val avatarIndex: Int = 0, // Profile picture index (0 to 5 for cute modern sci-fi avatars)
    val totalQuizzesCompleted: Int = 0,
    val totalSimulationsCompleted: Int = 0,
    val photoUrl: String = "",
    val coins: Int = 100
)

@Entity(tableName = "user_achievements")
data class UserAchievement(
    @PrimaryKey val id: String, // String ID for easy matching, e.g. "master_atom"
    val title: String,
    val description: String,
    val isEarned: Boolean = false,
    val earnedTimestamp: Long = 0L,
    val iconName: String // string name for corresponding icons
)

@Entity(tableName = "element_favorites")
data class ElementFavorite(
    @PrimaryKey val atomicNumber: Int,
    val isFavorite: Boolean = true
)

@Entity(tableName = "materi_progress")
data class MateriProgress(
    @PrimaryKey val materiId: String,
    val isCompleted: Boolean = false,
    val highScore: Int = 0
)

@Entity(tableName = "flashcard_status")
data class FlashcardStatus(
    @PrimaryKey val cardId: String,
    val isDifficult: Boolean = false,
    val isFavorite: Boolean = false
)

// Structural classes (non-Room, for the periodic table elements)
data class ChemicalElement(
    val number: Int,
    val symbol: String,
    val name: String,
    val mass: Double,
    val category: String, // Alkali, Halogen, Noble Gas, Transition Metal, Lanthanide, Actinide, Nonmetal, Metalloid, Alkaline Earth
    val period: Int,
    val group: Int, // 1 to 18 (0 for Lanthanides/Actinides)
    val electronConfig: String,
    val valenceElectrons: Int,
    val phase: String, // Gas, Liquid, Solid, Synthetic
    val discoveredBy: String,
    val discoveredYear: String,
    val uses: String,
    val interestingFact: String,
    val xPos: Int, // position in periodic table grid
    val yPos: Int
)

// Structural classes for Materi
data class SubMateri(
    val id: String,
    val title: String,
    val summary: String,
    val fullText: String,
    val challengeQuestion: String,
    val challengeOptions: List<String>,
    val challengeAnswerIndex: Int,
    val explanation: String
)

data class MateriCategory(
    val id: String,
    val title: String,
    val iconName: String, // structural identifier for icon
    val difficulty: String, // Pemula, Menengah, Mahir
    val estimatedMinutes: Int,
    val subMateriList: List<SubMateri>
)
