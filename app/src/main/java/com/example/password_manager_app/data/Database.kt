package com.example.password_manager_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.password_manager_app.model.User
import com.example.password_manager_app.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class PasswordManagerDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
