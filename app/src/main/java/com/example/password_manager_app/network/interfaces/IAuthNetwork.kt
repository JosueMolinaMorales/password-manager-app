package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.data.AuthResponse
import com.example.password_manager_app.data.LoginForm
import com.example.password_manager_app.data.RegisterForm
import com.example.password_manager_app.data.UpdateForm
import okhttp3.Response
import okhttp3.ResponseBody

interface IAuthNetwork {
    suspend fun login(loginForm: LoginForm): Response?
    suspend fun register(registerForm: RegisterForm): Response?
}