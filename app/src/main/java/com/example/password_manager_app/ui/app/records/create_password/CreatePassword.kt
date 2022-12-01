package com.example.password_manager_app.ui.app.records.create_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.app.records.components.generate_password.GeneratePasswordView
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.theme.Charcoal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreatePasswordPage(
    onGeneratePasswordClick: () -> Unit,
    onCreatePasswordClick: () -> Unit
) {
    val vm: CreatePasswordViewModel = viewModel()
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null) }
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val showPassword = remember { mutableStateOf(false) }
    ModalBottomSheetLayout(
        sheetContent = {
            GeneratePasswordView { password ->
                vm.setPassword(password)
                coroutineScope.launch {
                    bottomState.hide()
                }
            }
        },
        sheetState = bottomState,
        sheetBackgroundColor = Charcoal,
        scrimColor = Charcoal.copy(alpha = .5F)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Form Fields
                        PasswordManagerTextField(
                            value = vm.service.value,
                            onValueChange = vm::setService,
                            label = { Text(text = "Service*") },
                            isError = vm.serviceHasError.value
                        )
                        PasswordManagerTextField(
                            value = vm.email.value,
                            onValueChange = vm::setEmail,
                            label = { Text(text = "Email") },
                            isError = vm.emailHasError.value
                        )
                        PasswordManagerTextField(
                            value = vm.username.value,
                            onValueChange = vm::setUsername,
                            label = { Text(text = "Username") },
                            isError = vm.usernameHasError.value
                        )
                        PasswordManagerTextField(
                            value = vm.password.value,
                            onValueChange = vm::setPassword,
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
                            hideText = !showPassword.value,
                            isError = vm.passwordHasError.value
                        )
                        PasswordManagerButton(onClick = {
                            coroutineScope.launch {
                                bottomState.show()
                            }
                        }) {
                            Text(text = "Generate Password")
                        }
                    }
                    Row {
                        // Submit button
                        PasswordManagerButton(onClick = {
                            // Validate input
                            errorMsg.value = vm.validate()
                            if (errorMsg.value == null) {
                                // No error message, make request
                                // TODO: Call vm method to make request to api to create password
                                onCreatePasswordClick()
                            }
                        }) {
                            Text(text = "Create")
                        }
                    }
                }
            }
            if (errorMsg.value != null) {
                PasswordManagerSnackbar(
                    modifier = Modifier
                        .padding(4.dp)
                        .zIndex(1F),
                    action = {
                        PasswordManagerButton(
                            onClick = { errorMsg.value = null },
                        ) {
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
    }
}