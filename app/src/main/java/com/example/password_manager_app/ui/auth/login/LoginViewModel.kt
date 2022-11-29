package com.example.password_manager_app.ui.auth.login

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.password_manager_app.data.AuthResponse
import com.example.password_manager_app.data.LoginForm
import com.example.password_manager_app.data.PasswordManagerDatabase
import com.example.password_manager_app.network.ErrorResponse
import com.example.password_manager_app.ui.auth.network.AuthNetwork
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(app: Application): AndroidViewModel(app) {
    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> = _password

    private val _isMakingRequest: MutableState<Boolean> = mutableStateOf(false)
    val isMakingRequest: State<Boolean> = _isMakingRequest

    private val _emailHasError: MutableState<Boolean> = mutableStateOf(false)
    val emailHasError: State<Boolean> = _emailHasError
    private val _passwordHasError: MutableState<Boolean> = mutableStateOf(false)
    val passwordHasError: State<Boolean> = _passwordHasError

    private val userDb: PasswordManagerDatabase

    private val authNetwork = AuthNetwork()

    init {
        userDb = Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            "passwordManager.db"
        ).build()
        viewModelScope.launch {
            val user = userDb.userDao().getUser()
            if (user != null) {
                // If there was a user in db, populate email field
                _email.value = user.email
            }
        }
    }

    fun setEmail(email: String) {
        _email.value = email
        _emailHasError.value = _email.value == ""
    }

    fun setPassword(password: String) {
        _password.value = password
        _passwordHasError.value = _password.value == ""
    }

    fun validate(): String? {
        if (_email.value == "" && _password.value == "") {
            _emailHasError.value = true
            _passwordHasError.value = true
            return "Email and Password are required"
        }
        if (_email.value == "") {
            _emailHasError.value = true
            return "Email is required"
        }
        if (_password.value == "") {
            _passwordHasError.value = true
            return "Password is required"
        }
        return null
    }

    fun login(
        onSuccessfulLogin: () -> Unit,
        onUnsuccessfulLogin: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isMakingRequest.value = true
            val response = authNetwork.login(LoginForm(
                email = _email.value,
                password = password.value
            ))
            _isMakingRequest.value = false
            val responseBody = response.body
            when (response.code) {
                200 -> {
                    val jsonBody = responseBody?.string()
                    val authResponse = Gson().fromJson(jsonBody, AuthResponse::class.java)
                    // Add token to user
                    authResponse.user.token = authResponse.token

                    val recentUser = userDb.userDao().getUser()
                    if (recentUser == null) {
                        // No recent user, log the user in regularly
                        userDb.userDao().insertUser(authResponse.user)
                    } else if (recentUser.email != authResponse.user.email) {
                        // Delete previous user from table since users are different
                        userDb.userDao().deleteUsers()
                        // User does not exist in the db so add them
                        userDb.userDao().insertUser(authResponse.user)
                    } else {
                        // Previous user is attempting to login, replace token
                        userDb.userDao().updateUser(authResponse.user)
                    }

                    onSuccessfulLogin()
                }
                400, 404 -> {
                    val jsonBody = responseBody?.string()
                    val errorResponse = Gson().fromJson(jsonBody, ErrorResponse::class.java)
                    onUnsuccessfulLogin(errorResponse.error.message)
                }
                else -> {
                    onUnsuccessfulLogin("Internal Service Error, Please Try Again Later")
                }
            }
        }
    }
}