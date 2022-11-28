package com.example.password_manager_app.data

import android.app.Application
import androidx.room.Room

class PasswordManagerUserManager(app: Application) {
    private val db: PasswordManagerDatabase

    init {
        db = Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            "passwordManager.db"
        ).build()
    }

    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }

    suspend fun getToken(userId: String) {
        db.userDao().getToken(userId)
    }

    suspend fun getUser(userId: String) {
        db.userDao().getUser(userId)
    }
}