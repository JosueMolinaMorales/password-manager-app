package com.example.password_manager_app.ui.app.profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.model.User
import com.example.password_manager_app.ui.EditProfileSelection
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.LavenderBlush
import kotlinx.coroutines.delay

/**
 * The Edit User Info View, will show a different view based on what they would like to edit
 */
@Composable
fun EditUserInfoView(selection: EditProfileSelection, user: User?, onEditChange: (User) -> Unit) {
    val editUserViewModel: EditUserInfoViewModel = viewModel()
    when(selection){
        EditProfileSelection.Email -> {
            ChangeEmailView(
                editUserViewModel = editUserViewModel,
                onEditChange = onEditChange,
            )
        }
        EditProfileSelection.Password -> {
            ChangePasswordView(
                editUserViewModel = editUserViewModel,
                onEditChange = onEditChange,
            )
        }
    }
}


/**
 * View for changing a user's password
 */
@Composable
fun ChangePasswordView(editUserViewModel: EditUserInfoViewModel, onEditChange: (User) -> Unit) {
    val focusManager: FocusManager = LocalFocusManager.current
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null)}
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(true) }
    val showNewPassword: MutableState<Boolean> = remember { mutableStateOf(true) }
    val showConfirmPassword: MutableState<Boolean> = remember { mutableStateOf(true)}

    Column(
        Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Update Password",
            style = MaterialTheme.typography.h4,
            fontSize = 30.sp,
            color = LavenderBlush,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        PasswordManagerTextField(
            value = editUserViewModel.password.value,
            onValueChange = editUserViewModel::setPassword,
            placeholder = { Text(text = "Current Password*") },
            modifier = Modifier.padding(8.dp),
            hideText = showPassword.value,
            isHiddenField = true,
            onTrailingIconClick = { showPassword.value = !showPassword.value },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        PasswordManagerTextField(
            value = editUserViewModel.newPassword.value,
            onValueChange = editUserViewModel::setNewPassword,
            placeholder = { Text(text = "New Password*") },
            modifier = Modifier.padding(8.dp),
            hideText = showNewPassword.value,
            isHiddenField = true,
            onTrailingIconClick = { showNewPassword.value = !showNewPassword.value },
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        PasswordManagerTextField(
            value = editUserViewModel.confirmPassword.value,
            onValueChange = editUserViewModel::setConfirmPassword,
            placeholder = { Text(text = "Confirm New Password*") },
            modifier = Modifier.padding(8.dp),
            hideText = showConfirmPassword.value,
            isHiddenField = true,
            onTrailingIconClick = { showConfirmPassword.value = !showConfirmPassword.value },
            keyboardActions = KeyboardActions(
                onGo = {  }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        PasswordManagerButton(onClick = {
            errorMsg.value = editUserViewModel.validatePassword()
            if (errorMsg.value == null){
                editUserViewModel.updateInfo(
                    onSuccessfulUpdate = { newUser ->
                        onEditChange(newUser)
                    },
                    onUnsuccessfulUpdate = {
                        errorMsg.value = editUserViewModel.updateErrorMsg.value
                    },
                )
            }
            }
        ) {
            Text("Update Password")
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

/**
 * View for changing a users email
 */
@Composable
fun ChangeEmailView(editUserViewModel: EditUserInfoViewModel, onEditChange: (User) -> Unit,) {
    val focusManager: FocusManager = LocalFocusManager.current
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null)}
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Update Email",
            style = MaterialTheme.typography.h4,
            color = LavenderBlush,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        PasswordManagerTextField(
            value = editUserViewModel.email.value,
            onValueChange = editUserViewModel::setEmail,
            placeholder = { Text(text = "New Email*") },
            modifier = Modifier.padding(8.dp),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        PasswordManagerTextField(
            value = editUserViewModel.password.value,
            onValueChange = editUserViewModel::setPassword,
            placeholder = { Text(text = "Password*") },
            modifier = Modifier.padding(8.dp),
            hideText = showPassword.value,
            isHiddenField = true,
            onTrailingIconClick = { showPassword.value = !showPassword.value },
        )
        PasswordManagerButton(onClick = {
            errorMsg.value = editUserViewModel.validateEmail()
            if (errorMsg.value == null){
                editUserViewModel.updateInfo(
                    onSuccessfulUpdate = { user ->
                        onEditChange(user)
                    },
                    onUnsuccessfulUpdate = {
                        errorMsg.value = editUserViewModel.updateErrorMsg.value
                    },
                )
            }
        }
        ) {
            Text("Update Email")
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