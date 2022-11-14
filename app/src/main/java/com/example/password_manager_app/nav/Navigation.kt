package com.example.password_manager_app.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.password_manager_app.ui.HomeScreen
import com.example.password_manager_app.ui.LoginScreen
import com.example.password_manager_app.ui.RegisterScreen

@Composable
fun PasswordManagerNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "homeScreen" ) {
        composable("login") {
            LoginScreen(
                onSubmit = {},
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("homeScreen") {
            HomeScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToViewPasswords = {}
            )
        }
    }
}