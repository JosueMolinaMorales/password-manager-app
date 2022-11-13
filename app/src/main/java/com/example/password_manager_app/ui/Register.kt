package com.example.password_manager_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.R

@Composable
fun RegisterScreen(
    onNavigateToMainScreen: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Register",
                style = MaterialTheme.typography.h4
            )
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Name") },
                isError = false, // Will be true if the login failed
                modifier = Modifier
                    .padding(8.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Username") },
                isError = false, // Will be true if the login failed
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Email") },
                isError = false, // Will be true if the login failed
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Password") },
                isError = false, // Will be true if the login failed
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Confirm Password") },
                isError = false, // Will be true if the login failed
                modifier = Modifier.padding(8.dp)
            )
            Button(onClick = { onNavigateToMainScreen() }) {
                Text("Create Account")
            }
            Text(
                "Already have an account?",
                modifier = Modifier
                    .clickable { onNavigateToLogin() },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}