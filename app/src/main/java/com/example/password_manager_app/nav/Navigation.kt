package com.example.password_manager_app.nav

import android.content.ClipboardManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.password_manager_app.ui.app.main_screen.MainScreen
import com.example.password_manager_app.ui.auth.homescreen.HomeScreen
import com.example.password_manager_app.ui.auth.login.LoginScreen
import com.example.password_manager_app.ui.auth.register.RegisterScreen

/**
 * Outer navigation prior to being logged in
 */
@Composable
fun PasswordManagerNavigation(
    navController: NavHostController,
    clipboard: ClipboardManager
) {
    NavHost(navController = navController, startDestination = Routes.HomePage.route ) {
        composable(Routes.LoginPage.route) {
            LoginScreen(
                onNavigateToMainScreen = {
                    navController.navigate(Routes.MainScreenPage.route) {
                        popUpTo(Routes.HomePage.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Routes.RegisterPage.route) }
            )
        }
        composable(Routes.HomePage.route) {
            HomeScreen(
                onNavigateToLogin = { navController.navigate(Routes.LoginPage.route) },
                onNavigateToRegister = { navController.navigate(Routes.RegisterPage.route) }
            )
        }
        composable(Routes.RegisterPage.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Routes.LoginPage.route) },
                onNavigateToMainScreen = {
                    navController.navigate(Routes.MainScreenPage.route) {
                        popUpTo(Routes.HomePage.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.MainScreenPage.route) {
            MainScreen(onLogOut = {
                navController.navigate(Routes.LoginPage.route) {
                    popUpTo(Routes.MainScreenPage.route) { inclusive = true }
                }
            },
                clipboard = clipboard
            )
        }
    }
}