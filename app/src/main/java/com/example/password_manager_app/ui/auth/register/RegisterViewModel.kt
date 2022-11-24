package com.example.password_manager_app.ui.auth.register

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password_manager_app.data.AuthResponse
import com.example.password_manager_app.data.RegisterForm
import com.example.password_manager_app.network.ErrorResponse
import com.example.password_manager_app.ui.auth.network.AuthNetwork
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val _name: MutableState<String> = mutableStateOf("")
    val name: State<String> = _name
    private val _nameHasError: MutableState<Boolean> = mutableStateOf(false)
    val nameHasError: State<Boolean> = _nameHasError

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email
    private val _emailHasError: MutableState<Boolean> = mutableStateOf(false)
    val emailHasError: State<Boolean> = _emailHasError

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> = _username
    private val _usernameHasError: MutableState<Boolean> = mutableStateOf(false)
    val usernameHasError: State<Boolean> = _usernameHasError

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> = _password
    private val _passwordHasError: MutableState<Boolean> = mutableStateOf(false)
    val passwordHasError: State<Boolean> = _passwordHasError

    private val _confirmPassword: MutableState<String> = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword
    private val _confirmPasswordHasError: MutableState<Boolean> = mutableStateOf(false)
    val confirmPasswordHasError: State<Boolean> = _confirmPasswordHasError

    private val _registerErrorMsg: MutableState<String?> = mutableStateOf(null)
    val registerErrorMsg: State<String?> = _registerErrorMsg

    private val _makingRequest: MutableState<Boolean> = mutableStateOf(false)
    val makingRequest: State<Boolean> = _makingRequest

    private val authNetwork: AuthNetwork = AuthNetwork()

    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
        _confirmPasswordHasError.value = _confirmPassword.value != _password.value
        _passwordHasError.value = _confirmPassword.value != _password.value
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _confirmPasswordHasError.value = _confirmPassword.value != _password.value
        _passwordHasError.value = _confirmPassword.value != _password.value
    }
    /**
     * Validate the register form
     */
    fun validate(): String? {
        // Check required fields
        var errorMsg = ""
        val emptyFields = mutableListOf<String>()
        if (_name.value == "") {
            _nameHasError.value = true
            emptyFields.add("name")
        }
        if (_username.value == "") {
            _usernameHasError.value = true
            emptyFields.add("username")
        }
        if (_email.value == "") {
            _emailHasError.value = true
            emptyFields.add("email")
        }
        if (_password.value == "") {
            _passwordHasError.value = true
            emptyFields.add("password")
        }
        if (_confirmPassword.value == "") {
            _confirmPasswordHasError.value = true
            emptyFields.add("confirm password")
        }
        // Check to see if there was empty field error
        if (emptyFields.size != 0) {
            emptyFields.forEachIndexed { index, value ->
                errorMsg += if (index != (emptyFields.size-1)) { "$value, " } else { value }
            }
            return if (emptyFields.size == 1) {
                "$errorMsg is required"
            } else {
                "$errorMsg are required"
            }
        }
        // Check Password matches
        if (_password.value != _confirmPassword.value) {
            _confirmPasswordHasError.value = _confirmPassword.value != _password.value
            _passwordHasError.value = _confirmPassword.value != _password.value
            return "Passwords do no match"
        }

        return null
    }

    fun register(
        onSuccessfulRegistration: () -> Unit,
        onUnsuccessfulRegistration: () -> Unit
    ) {
        viewModelScope.launch {
            _makingRequest.value = true
            val response = authNetwork.register(RegisterForm(
                name = _name.value,
                username = _username.value,
                email = _email.value,
                password = _password.value
            ))
            _makingRequest.value = false
            val body = response.body?.string()
            when (response.code) {
                201, 200 -> {
                    val responseBody = Gson().fromJson(body, AuthResponse::class.java)
                    onSuccessfulRegistration()
                }
                400 -> {
                    val errorBody = Gson().fromJson(body,ErrorResponse::class.java)
                    _registerErrorMsg.value = errorBody.error.message
                    onUnsuccessfulRegistration()
                }
                else -> {
                    _registerErrorMsg.value = "Internal Service Error, Please try again later"
                    onUnsuccessfulRegistration()
                }
            }
        }
    }
}