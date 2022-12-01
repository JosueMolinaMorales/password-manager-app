package com.example.password_manager_app.ui.app.records.create_password

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CreatePasswordViewModel: ViewModel() {

    private val _service: MutableState<String> = mutableStateOf("")
    val service: State<String> = _service

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> = _username

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> = _password

    private val _serviceHasError: MutableState<Boolean> = mutableStateOf(false)
    val serviceHasError: State<Boolean> = _serviceHasError

    private val _emailHasError: MutableState<Boolean> = mutableStateOf(false)
    val emailHasError: State<Boolean> = _emailHasError

    private val _usernameHasError: MutableState<Boolean> = mutableStateOf(false)
    val usernameHasError: State<Boolean> = _usernameHasError

    private val _passwordHasError: MutableState<Boolean> = mutableStateOf(false)
    val passwordHasError: State<Boolean> = _passwordHasError

    fun setService(service: String) {
        _service.value = service
        _serviceHasError.value = _service.value == ""
    }

    fun setEmail(email: String) {
        _email.value = email
        _emailHasError.value = emailHasError.value && _email.value == ""
    }

    fun setUsername(username: String) {
        _username.value = username
        _usernameHasError.value = usernameHasError.value && _username.value == ""
    }

    fun setPassword(password: String) {
        _password.value = password
        _passwordHasError.value = _password.value == ""
    }

    fun validate(): String? {
        // Required Fields
        if (_service.value == "" && _password.value == "") {
            _serviceHasError.value = true
            _passwordHasError.value = true
            return "Service and Password are required"
        }
        if (_service.value == "") {
            _serviceHasError.value = true
            return "Service is required"
        }
        if (_password.value == "") {
            _passwordHasError.value = true
            return "Password is required"
        }

        // Either Email or Username need to be present
        if (_email.value == "" && _username.value == "") {
            return "Either email or username is required"
        }
        return null
    }
}