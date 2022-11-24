package com.example.password_manager_app.ui.auth.network

import com.example.password_manager_app.data.AuthResponse
import com.example.password_manager_app.data.LoginForm
import com.example.password_manager_app.data.RegisterForm
import okhttp3.Response

interface IAuthNetwork {
    suspend fun login(loginForm: LoginForm): Response
    suspend fun register(registerForm: RegisterForm): Response
}