package com.example.password_manager_app.network.auth

import android.net.ConnectivityManager
import com.example.password_manager_app.model.LoginForm
import com.example.password_manager_app.model.RegisterForm
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IAuthNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Network class for handling auth related calls to the API
 */
class AuthNetwork(private val connectivityManager: ConnectivityManager): IAuthNetwork {
    private val client = OkHttpClient()

    /**
     * Attempts to log in with the given credentials.
     *
     * @param loginForm An object containing the login credentials.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun login(loginForm: LoginForm): Response? {
        if (connectivityManager.activeNetwork != null) {
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

    /**
     * Attempts to register a new user with the given credentials.
     *
     * @param registerForm An object containing the registration information.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun register(registerForm: RegisterForm): Response? {
        if (connectivityManager.activeNetwork != null) {
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