package com.example.password_manager_app.network

enum class Routes(val route: String) {
    LocalHost("http://10.0.2.2:8000"),
    PasswordManagerRoute("https://password-manager-vig6p.ondigitalocean.app")
}