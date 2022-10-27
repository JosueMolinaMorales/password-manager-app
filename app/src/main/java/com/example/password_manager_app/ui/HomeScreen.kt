package com.example.password_manager_app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Home Screen")
        Button(onClick = onNavigateToLogin) {
            Text("Login")
        }
        Button(onClick = {}) {
            Text("Register")
        }
    }
}