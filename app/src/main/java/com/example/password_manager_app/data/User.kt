package com.example.password_manager_app.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
    val name: String,
    val email: String,
    val username: String,
) {}

class RegisterForm(
    var name: String = "",
    var email: String = "",
    var username: String = "",
    var password: String = "",

    @Expose(serialize = false)
    var confirmPassword: String = ""
) {}

class LoginForm(
    val email: String = "",
    val password: String = ""
)

data class AuthResponse(
    val user: User,
    val token: String
)