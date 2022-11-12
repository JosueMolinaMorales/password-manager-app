package com.example.password_manager_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField


/**
 * Login Screen that lets a user log in
 */
@Composable
fun LoginScreen(
    onSubmit: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.koalalogo),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                // Email Row
                PasswordManagerTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "Email") },
                    isError = false // Will be true if the login failed
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                // Password Row
                PasswordManagerTextField(
                    value = "",
                    label = { Text("Password") },
                    onValueChange = {},
                    placeholder = { Text(text = "Password") },
                    isError = false // Will be true if the login failed
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            ) {
                PasswordManagerButton(onClick = onSubmit) {
                    Text(text = "Login")
                }
            }
            Row {
                Text(
                    text = "No Account? Register",
                    modifier = Modifier
                        .clickable { onNavigateToRegister() },
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}