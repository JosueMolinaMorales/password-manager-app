package com.example.password_manager_app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.ui.theme.LavenderBlush


@Composable
fun BottomSheetComponent(
    onCreatePasswordClick: () -> Unit,
    onCreateSecretClick: () -> Unit,
    onGeneratePassword: () -> Unit,
    isOnPasswordPage: Boolean,
) {
    if (isOnPasswordPage) {
        GeneratePasswordView(
            onGeneratePassword
        )
    } else {
        CreateRecordView(
            onCreatePasswordClick,
            onCreateSecretClick
        )
    }
}


@Composable
fun GeneratePasswordView(
    onGeneratePassword: () -> Unit
) {
    // Show generate Password
    val sliderPosition = remember { mutableStateOf(8) }
    val includeCapitalLetter = remember { mutableStateOf(true) }
    val includeNumbers = remember { mutableStateOf(true) }
    val includeSpecialCharacters = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Generate Password",
            style = MaterialTheme.typography.h4,
            color = LavenderBlush,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(8.dp)
        )
        Divider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Character Length: ${sliderPosition.value}",
                color = LavenderBlush,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.h6
            )
            Slider(
                value = sliderPosition.value.toFloat(),
                onValueChange = { sliderPosition.value = it.toInt() },
                valueRange = 8F..20F,
                modifier = Modifier.fillMaxWidth(.8F)
            )
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = includeCapitalLetter.value,
                    onCheckedChange = { includeCapitalLetter.value = it }
                )
                Text(text = "A-Z")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = includeNumbers.value,
                    onCheckedChange = { includeNumbers.value = it }
                )
                Text(text = "0-9")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = includeSpecialCharacters.value,
                    onCheckedChange = { includeSpecialCharacters.value = it }
                )
                Text(text = "!@#$%")
            }
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            PasswordManagerButton(onClick = onGeneratePassword) {
                Text("Generate")
            }
        }
    }
}

@Composable
fun CreateRecordView(
    onCreatePasswordClick: () -> Unit,
    onCreateSecretClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1F),
            horizontalArrangement = Arrangement.Center
        ) {
            // Create New Title
            Text(
                text = "Create New",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                color = LavenderBlush
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1F),
            horizontalArrangement = Arrangement.Center
        ) {
            // Password Button
            PasswordManagerButton(onClick = onCreatePasswordClick) {
                Text("Password")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1F),
            horizontalArrangement = Arrangement.Center
        ) {
            // Secret Button
            PasswordManagerButton(onClick = onCreateSecretClick) {
                Text("Secret")
            }
        }
    }
}