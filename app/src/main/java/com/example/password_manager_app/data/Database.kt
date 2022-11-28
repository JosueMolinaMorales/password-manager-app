package com.example.password_manager_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class PasswordManagerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
