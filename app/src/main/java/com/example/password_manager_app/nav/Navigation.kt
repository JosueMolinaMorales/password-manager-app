package com.example.password_manager_app.nav

import android.content.ClipboardManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.password_manager_app.ui.*

@Composable
fun PasswordManagerNavigation(
    navController: NavHostController,
    clipboard: ClipboardManager
) {
    NavHost(navController = navController, startDestination = "homeScreen" ) {
        composable("login") {
            LoginScreen(
                onNavigateToMainScreen = {
                    navController.navigate("mainScreen") {
                        popUpTo("homeScreen") { inclusive = true }
                    }
                },
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
                onNavigateToMainScreen = {
                    navController.navigate("mainScreen") {
                        popUpTo("homeScreen") { inclusive = true }
                    }
                }
            )
        }
        composable("mainScreen") {
            MainScreen(onLogOut = {
                navController.navigate("login") {
                    popUpTo("mainScreen") { inclusive = true }
                }
            },
                clipboard = clipboard
            )
        }
    }
}