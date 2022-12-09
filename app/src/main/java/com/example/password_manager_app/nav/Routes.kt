package com.example.password_manager_app.nav

sealed class Routes(val route: String) {
    object LoginPage: Routes("login")
    object HomePage: Routes("homeScreen")
    object RegisterPage: Routes("register")
    object ProfilePage: Routes("profile")
    object MainScreenPage: Routes("mainScreen")
}