package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.UserProfile
import com.example.data.model.UserAchievement
import com.example.data.model.ElementFavorite
import com.example.data.model.MateriProgress
import com.example.data.model.FlashcardStatus

@Database(
    entities = [
        UserProfile::class,
        UserAchievement::class,
        ElementFavorite::class,
        MateriProgress::class,
        FlashcardStatus::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "neochem_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
