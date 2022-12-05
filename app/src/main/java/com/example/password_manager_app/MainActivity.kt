package com.example.password_manager_app

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.nav.PasswordManagerNavigation
import com.example.password_manager_app.ui.LoginScreen
import com.example.password_manager_app.ui.theme.PasswordmanagerappTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ServiceCast")
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
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                    PasswordManagerNavigation(navController = navController, clipboard = clipboard)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PasswordmanagerappTheme {
        LoginScreen(onNavigateToMainScreen = {}, onNavigateToRegister = {})
    }
}

/**
 * Loading Images: https://developer.android.com/jetpack/compose/graphics/images/loading
 * Navigation: https://developer.android.com/jetpack/compose/navigation
 * Clipboard: https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#kotlin
 */