package com.example.password_manager_app.network.auth

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.password_manager_app.data.LoginForm
import com.example.password_manager_app.data.RegisterForm
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IAuthNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody

class AuthNetwork(connectivityManager: ConnectivityManager): IAuthNetwork {
    private val client = OkHttpClient()
    private val network: Network? = connectivityManager.activeNetwork
//TODO something with the connectivity manager

    override suspend fun login(loginForm: LoginForm): Response? {
        if (network != null) {
            return withContext(Dispatchers.IO) {
                val requestBody = Gson().toJson(loginForm).toRequestBody()
                val request = Request
                    .Builder()
                    .post(requestBody)
                    .url("${Routes.PasswordManagerRoute.route}/auth/login")
                    .build()
                client.newCall(request).execute()
            }
        } else {
            return null
        }
    }

    override suspend fun register(registerForm: RegisterForm): Response? {
        if (network != null) {
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
        } else {
            return null
        }
    }
}