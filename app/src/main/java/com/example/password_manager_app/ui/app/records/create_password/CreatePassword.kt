package com.example.password_manager_app.ui.app.records.create_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.R

@Composable
fun CreatePasswordPage(
    onGeneratePasswordClick: () -> Unit,
    onCreatePasswordClick: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(.3F)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Create Password",
                style = MaterialTheme.typography.h4,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.drawable.koalalogo), contentDescription = "")
        }
        Column(
            modifier = Modifier.fillMaxHeight(.75F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Form Fields
            PasswordManagerTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Service*") },
            )
            PasswordManagerTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Email") }
            )
            PasswordManagerTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Username*") }
            )
            PasswordManagerTextField(
                value = "",
                onValueChange = { },
                label = { Text(text = "Password*") },
                trailingIcon = { IconToggleButton(
                    checked = showPassword.value,
                    onCheckedChange = { showPassword.value = !showPassword.value }
                ) {
                    if (showPassword.value) {
                        Icon(Icons.Filled.Visibility, "")
                    } else {
                        Icon(Icons.Filled.VisibilityOff, "")
                    }
                }},
                hideText = !showPassword.value
            )
            PasswordManagerButton(onClick = onGeneratePasswordClick) {
                Text(text = "Generate Password")
            }
        }
        Row {
            // Submit button
            PasswordManagerButton(onClick = onCreatePasswordClick) {
                Text(text = "Create")
            }
        }
    }
}