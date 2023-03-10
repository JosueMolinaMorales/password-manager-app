package com.example.password_manager_app.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.Charcoal
import kotlinx.coroutines.delay

/**
 * The Register screen
 */
@Composable
fun RegisterScreen(
    onNavigateToMainScreen: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val registerViewModel: RegisterViewModel = viewModel()
    val focusManager: FocusManager = LocalFocusManager.current
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null) }
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(true) }
    val showConfirmPassword: MutableState<Boolean> = remember { mutableStateOf(true) }

    fun onRegister() {
        errorMsg.value = registerViewModel.validate()
        if (errorMsg.value == null) {
            // no error message, make register request
            registerViewModel.register(
                onSuccessfulRegistration = onNavigateToMainScreen,
                onUnsuccessfulRegistration = {
                    errorMsg.value = registerViewModel.registerErrorMsg.value
                }
            )
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Register",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.koalalogo),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(.3F)
                )
            }
            PasswordManagerTextField(
                value = registerViewModel.name.value,
                onValueChange = registerViewModel::setName,
                placeholder = { Text(text = "Name*") },
                isError = registerViewModel.nameHasError.value, // Will be true if the login failed
                modifier = Modifier.padding(8.dp),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            PasswordManagerTextField(
                value = registerViewModel.username.value,
                onValueChange = registerViewModel::setUsername,
                placeholder = { Text(text = "Username*") },
                isError = registerViewModel.usernameHasError.value, // Will be true if the login failed
                modifier = Modifier.padding(8.dp),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            PasswordManagerTextField(
                value = registerViewModel.email.value,
                onValueChange = registerViewModel::setEmail,
                placeholder = { Text(text = "Email*") },
                isError = registerViewModel.emailHasError.value, // Will be true if the login failed
                modifier = Modifier.padding(8.dp),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            PasswordManagerTextField(
                value = registerViewModel.password.value,
                onValueChange = registerViewModel::setPassword,
                placeholder = { Text(text = "Password*") },
                isError = registerViewModel.passwordHasError.value, // Will be true if the login failed
                modifier = Modifier.padding(8.dp),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                hideText = showPassword.value,
                isHiddenField = true,
                onTrailingIconClick = { showPassword.value = !showPassword.value }
            )
            PasswordManagerTextField(
                value = registerViewModel.confirmPassword.value,
                onValueChange = registerViewModel::setConfirmPassword,
                placeholder = { Text(text = "Confirm Password*") },
                isError = registerViewModel.confirmPasswordHasError.value, // Will be true if the login failed
                modifier = Modifier.padding(8.dp),
                hideText = showConfirmPassword.value,
                isHiddenField = true,
                onTrailingIconClick = { showConfirmPassword.value = !showConfirmPassword.value },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = { onRegister() }
                )
            )
            PasswordManagerButton(
                onClick = { onRegister() },
                enabled = !registerViewModel.makingRequest.value
            ) {
                if (registerViewModel.makingRequest.value) {
                    CircularProgressIndicator(color = Charcoal)
                } else {
                    Text("Create Account")
                }
            }
            Text(
                "Already have an account?",
                modifier = Modifier
                    .clickable { onNavigateToLogin() },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
    if (errorMsg.value != null) {
        PasswordManagerSnackbar(
            modifier = Modifier
                .padding(8.dp)
                .zIndex(1F),
            action = {
                PasswordManagerButton(onClick = { errorMsg.value = null }) {
                    Text("Confirm")
                }
            }
        ) {
            Text(text = errorMsg.value ?: "An error occurred")
            LaunchedEffect(key1 = errorMsg.value) {
                delay(5000)
                errorMsg.value = null
            }
        }
    }
}