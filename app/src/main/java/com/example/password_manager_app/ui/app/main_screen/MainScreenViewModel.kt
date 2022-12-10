package com.example.password_manager_app.ui.app.main_screen

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.password_manager_app.data.PasswordManagerDatabase
import com.example.password_manager_app.model.User
import kotlinx.coroutines.launch

/**
 * The MainScreenViewModel that handles getting the user from the db and updating them
 */
class MainScreenViewModel(app: Application): AndroidViewModel(app) {
    private val db: PasswordManagerDatabase
    private val _user: MutableState<User?> = mutableStateOf(null)
    private val _isLoggedIn: MutableState<Boolean> = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    val user: State<User?> = _user

    init {
        db = Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            "passwordManager.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        viewModelScope.launch {
            _user.value = db.userDao().getUser()
        }
    }

    fun logUserIn() {
        viewModelScope.launch {
            _user.value = db.userDao().getUser()
            db.userDao().updateUserLoggedInStatus(true, _user.value?.id ?: "")
            _isLoggedIn.value = true
        }
    }

    fun logUserOut() {
        viewModelScope.launch {
            db.userDao().updateUserLoggedInStatus(false, _user.value?.id ?: "")
            _isLoggedIn.value = false
        }
    }
    /**
     * Updates the user with a new user
     */
    fun updateUser(newUser: User) {
        _user.value = newUser
    }
}