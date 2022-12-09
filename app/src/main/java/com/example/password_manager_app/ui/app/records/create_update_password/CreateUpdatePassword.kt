package com.example.password_manager_app.ui.app.records.create_update_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.R
import com.example.password_manager_app.model.ActionOnRecord
import com.example.password_manager_app.ui.app.records.components.generate_password.GeneratePasswordView
import com.example.password_manager_app.ui.components.NotFoundPopUp
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.theme.Charcoal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The Create/Update Page for a password record
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateUpdatePasswordPage(
    action: ActionOnRecord,
    recordId: String?,
    token: String,
    onNavigateHome: () -> Unit
) {
    val vm: CreateUpdatePasswordViewModel = viewModel()
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null) }
    val show404Message: MutableState<Boolean> = remember { mutableStateOf(false) }
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val showPassword = remember { mutableStateOf(false) }

    // Check action type, if update, get record.
    if (action == ActionOnRecord.Update && recordId != null) {
        vm.getRecord(
            recordId,
            token,
            onError = {
                errorMsg.value = it
            },
            onNotFound = {
                show404Message.value = true
            }
        )
    }

    if (show404Message.value) {
        NotFoundPopUp(title = "Record Not Found") {
            onNavigateHome()
        }
    } else {
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
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Column(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Header
                            Text(
                                text = if (action == ActionOnRecord.Create) { "Create Password" } else { "Update Password" },
                                style = MaterialTheme.typography.h4,
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                            Image(
                                painter = painterResource(id = R.drawable.koalalogo),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(.4F)
                            )
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
                                isError = vm.serviceHasError.value,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            )
                            PasswordManagerTextField(
                                value = vm.email.value,
                                onValueChange = vm::setEmail,
                                label = { Text(text = "Email") },
                                isError = vm.emailHasError.value,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            )
                            PasswordManagerTextField(
                                value = vm.username.value,
                                onValueChange = vm::setUsername,
                                label = { Text(text = "Username") },
                                isError = vm.usernameHasError.value,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
                                isError = vm.passwordHasError.value,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
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
                                    if (action == ActionOnRecord.Create) {
                                        vm.createPassword(
                                            token = token,
                                            onSuccessfulCreation = onNavigateHome,
                                            onUnsuccessfulCreation = { errorMessage ->
                                                errorMsg.value = errorMessage
                                            }
                                        )
                                    } else {
                                        vm.updatePassword(
                                            token = token,
                                            recordId = recordId!!,
                                            onSuccess = onNavigateHome,
                                            onError = {
                                                errorMsg.value = it
                                            }
                                        )
                                    }
                                }
                            }) {
                                if (vm.isMakingRequest.value) {
                                    CircularProgressIndicator(color = Color.Black)
                                } else {
                                    Text(text = if (action == ActionOnRecord.Create) { "Create" } else { "Update" })
                                }
                            }
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
