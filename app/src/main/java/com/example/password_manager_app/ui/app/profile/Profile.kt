package com.example.password_manager_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.model.User
import com.example.password_manager_app.ui.app.profile.components.EditUserInfoView
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.theme.Charcoal
import kotlinx.coroutines.launch

enum class EditProfileSelection{
    Password,
    Email
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Profile(
    user: User,
    onEditChange: (User) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val selection = remember { mutableStateOf(EditProfileSelection.Password) }
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            EditUserInfoView(
                selection = selection.value,
                user = user,
                onEditChange = { user ->
                    coroutineScope.launch {
                        bottomState.hide()
                    }
                    onEditChange(user)
                }
            )
        },
        sheetState = bottomState,
        sheetBackgroundColor = Charcoal,
        scrimColor = Charcoal.copy(alpha = .5F)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Greeting
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Hi ${user.name}!",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)

            //Name
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Name",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = user.name,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)

            //email
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Email",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = user.email,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Divider(color = Color.Black, thickness = 1.dp)

            //username
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Username",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = user.username,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)

            //Edit Email
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                PasswordManagerButton(onClick = {
                    coroutineScope.launch {
                        selection.value = EditProfileSelection.Email
                        bottomState.show()
                    }
                }) {
                    Text(text = "Edit Email")
                }
            }


            //Change Password
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                PasswordManagerButton(onClick = {
                    coroutineScope.launch {
                        selection.value = EditProfileSelection.Password
                        bottomState.show()
                    }
                }) {
                    Text(text = "Change Password")
                }
            }
        }
    }

}