package com.example.password_manager_app.nav

sealed class Routes(val route: String) {
    object LoginPage: Routes("login")
    object HomePage: Routes("homepage")
    object RegisterPage: Routes("register")
}