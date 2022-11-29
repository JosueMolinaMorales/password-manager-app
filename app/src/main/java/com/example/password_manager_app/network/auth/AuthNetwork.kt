package com.example.password_manager_app.network.auth

import com.example.password_manager_app.data.LoginForm
import com.example.password_manager_app.data.RegisterForm
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IAuthNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody

class AuthNetwork: IAuthNetwork {
    private val client = OkHttpClient()

    override suspend fun login(loginForm: LoginForm): Response {
        return withContext(Dispatchers.IO) {
            val requestBody = Gson().toJson(loginForm).toRequestBody()
            val request = Request
                .Builder()
                .post(requestBody)
                .url("${Routes.PasswordManagerRoute.route}/auth/login")
                .build()
            client.newCall(request).execute()
        }
    }

    override suspend fun register(registerForm: RegisterForm): Response {
        return withContext(Dispatchers.IO) {
            val requestBody = Gson().toJson(registerForm).toRequestBody()
            val request = Request
                .Builder()
                .post(requestBody)
                .url("${Routes.PasswordManagerRoute.route}/auth/register")
                .build()
            val response = client.newCall(request).execute()
            response
        }
    }
}