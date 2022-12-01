package com.example.password_manager_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.R
import com.example.password_manager_app.data.AuthResponse
import com.example.password_manager_app.ui.auth.login.LoginViewModel
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.Charcoal
import kotlinx.coroutines.delay


/**
 * Login Screen that lets a user log in
 */
@Composable
fun LoginScreen(
    onNavigateToMainScreen: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val loginViewModel: LoginViewModel = viewModel()
    val hidePassword: MutableState<Boolean> = remember { mutableStateOf(true) }
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null) }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
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
                            value = loginViewModel.email.value,
                            onValueChange = loginViewModel::setEmail,
                            placeholder = { Text(text = "Email") },
                            isError = loginViewModel.emailHasError.value,
                            label = { Text(text = "Email*") }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        // Password Row
                        PasswordManagerTextField(
                            value = loginViewModel.password.value,
                            label = { Text("Password*") },
                            onValueChange = loginViewModel::setPassword,
                            placeholder = { Text(text = "Password") },
                            isError = loginViewModel.passwordHasError.value,
                            isHiddenField = true,
                            hideText = hidePassword.value,
                            onTrailingIconClick = { hidePassword.value = !hidePassword.value }
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    ) {
                        PasswordManagerButton(
                            onClick = {
                                errorMsg.value = loginViewModel.validate()
                                if (errorMsg.value == null) {
                                    loginViewModel.login(
                                        onSuccessfulLogin = {
                                            onNavigateToMainScreen()
                                        },
                                        onUnsuccessfulLogin = { errMsg ->
                                            errorMsg.value = errMsg
                                        }
                                    )
                                }
                            }
                        ) {
                            if (loginViewModel.isMakingRequest.value) {
                                CircularProgressIndicator(color = Charcoal)
                            } else {
                                Text(text = "Login")
                            }
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
    }
    if (errorMsg.value != null) {
        PasswordManagerSnackbar(
            modifier = Modifier.padding(8.dp).zIndex(1F),
            action = {
                PasswordManagerButton(onClick = { errorMsg.value = null }) {
                    Text(text = "Confirm")
                }
            }
        ) {
            Text(text = errorMsg.value ?: "An Error Occurred")
            LaunchedEffect(key1 = errorMsg.value) {
                delay(5000)
                errorMsg.value = null
            }
        }
    }

}