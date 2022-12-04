package com.example.password_manager_app.ui.app.records.create_secret

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import kotlinx.coroutines.delay

@Composable
fun CreateSecretPage(
    token: String,
    onCreateSecret: () -> Unit
) {
    val vm: CreateSecretViewModel = viewModel()
    val errorMsg: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                    // Form Fields
                    PasswordManagerTextField(
                        value = vm.key.value,
                        onValueChange = vm::setKey,
                        label = { Text(text = "Key*") },
                        isError = vm.keyHasError.value
                    )
                    Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
                    PasswordManagerTextField(
                        value = vm.secret.value,
                        onValueChange = vm::setSecret,
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
                        hideText = !showSecret.value,
                        isError = vm.secretHasError.value
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Submit button
                        PasswordManagerButton(
                            onClick = {
                                errorMsg.value = vm.validate()
                                if (errorMsg.value == null) {
                                    // No error, make request
                                    vm.createSecret(
                                        token = token,
                                        onSuccessfulSecretCreate = onCreateSecret,
                                        onUnsuccessfulCreate = { errMsg ->
                                            errorMsg.value = errMsg
                                        }
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(.4F)
                        ) {
                            if (vm.isMakingRequest.value) {
                                CircularProgressIndicator(color = Color.Black)
                            } else {
                                Text(text = "Create")
                            }
                        }
                    }
                }
            }
        }
        if (errorMsg.value != null) {
            PasswordManagerSnackbar(
                modifier = Modifier
                    .padding(8.dp)
                    .zIndex(1F),
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

}