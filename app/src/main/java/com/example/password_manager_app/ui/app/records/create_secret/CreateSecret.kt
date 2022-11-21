package com.example.password_manager_app.ui.app.records.create_secret

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
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField

@Composable
fun CreateSecretPage(
    onCreateSecret: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val showSecret = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxHeight(.5F),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Header and Icons
            Text(
                text = "Create Secret",
                style = MaterialTheme.typography.h4,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.drawable.koalalogo), contentDescription = "")
        }
        Column(
            modifier = Modifier.fillMaxHeight(.5F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Form Fields
            PasswordManagerTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Key*") }
            )
            PasswordManagerTextField(
                value = "",
                onValueChange = { },
                label = { Text(text = "Secret*") },
                trailingIcon = { IconToggleButton(
                    checked = showSecret.value,
                    onCheckedChange = { showSecret.value = !showSecret.value }
                ) {
                    if (showSecret.value) {
                        Icon(Icons.Filled.Visibility, "")
                    } else {
                        Icon(Icons.Filled.VisibilityOff, "")
                    }
                }
                },
                hideText = !showSecret.value
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Submit button
            PasswordManagerButton(
                onClick = onCreateSecret,
                modifier = Modifier.fillMaxWidth(.4F)
            ) {
                Text(text = "Create")
            }
        }
    }
}