package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.LoginForm
import com.example.password_manager_app.model.RegisterForm
import okhttp3.Response

interface IAuthNetwork {
    /**
     * Attempts to log in with the given credentials.
     *
     * @param loginForm An object containing the login credentials.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun login(loginForm: LoginForm): Response?

    /**
     * Attempts to register a new user with the given credentials.
     *
     * @param registerForm An object containing the registration information.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun register(registerForm: RegisterForm): Response?
}