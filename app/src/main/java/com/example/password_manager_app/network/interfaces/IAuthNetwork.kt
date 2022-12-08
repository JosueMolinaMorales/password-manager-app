package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.LoginForm
import com.example.password_manager_app.model.RegisterForm
import okhttp3.Response

interface IAuthNetwork {
    suspend fun login(loginForm: LoginForm): Response?
    suspend fun register(registerForm: RegisterForm): Response?
}