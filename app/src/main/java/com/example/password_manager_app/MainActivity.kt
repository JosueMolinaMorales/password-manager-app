package com.example.password_manager_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.ui.HomeScreen
import com.example.password_manager_app.ui.LoginScreen
import com.example.password_manager_app.ui.RegisterScreen
import com.example.password_manager_app.ui.theme.PasswordmanagerappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordmanagerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
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
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PasswordmanagerappTheme {
        LoginScreen(onSubmit = {}, onNavigateToRegister = {})
    }
}

/**
 * Loading Images: https://developer.android.com/jetpack/compose/graphics/images/loading
 * Navigation: https://developer.android.com/jetpack/compose/navigation
 */