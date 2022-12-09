package com.example.password_manager_app.ui.app.records.components.generate_password

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.theme.LavenderBlush

/**
 * The Bottom Sheet View for Generating a new password
 */
@Composable
fun GeneratePasswordView(
    onGeneratePassword: (String) -> Unit
) {
    // Show generate Password
    val vm: GeneratePasswordViewModel = viewModel()
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
                text = "Character Length: ${vm.sliderPosition.value}",
                color = LavenderBlush,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.h6
            )
            Slider(
                value = vm.sliderPosition.value.toFloat(),
                onValueChange = vm::setSliderPosition,
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
                    checked = vm.includeCapitalLetters.value,
                    onCheckedChange = vm::setCapitalLettersChoice
                )
                Text(text = "A-Z")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = vm.includeNumbers.value,
                    onCheckedChange = vm::setNumbersChoice
                )
                Text(text = "0-9")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = vm.includeSpecialCharacters.value,
                    onCheckedChange = vm::setSpecialCharacterChoice
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
            PasswordManagerButton(onClick = {
                onGeneratePassword(vm.generatePassword())
            }) {
                Text("Generate")
            }
        }
    }
}