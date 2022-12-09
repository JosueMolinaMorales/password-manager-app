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
    val user: State<User?> = _user

    init {
        db = Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            "passwordManager.db"
        ).build()
        viewModelScope.launch {
            _user.value = db.userDao().getUser()
        }
    }

    /**
     * Updates the user with a new user
     */
    fun updateUser(newUser: User) {
        _user.value = newUser
    }
}