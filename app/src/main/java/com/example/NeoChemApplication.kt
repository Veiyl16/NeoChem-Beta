package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.repository.UserRepository

class NeoChemApplication : Application() {
    // Lazily initialize the shared database and repository instances
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.appDao()) }
    val firebaseRepository by lazy { com.example.data.repository.FirebaseRepository(this) }

    override fun onCreate() {
        super.onCreate()
        com.example.data.local.NeoChemSettings.init(this)
    }
}
