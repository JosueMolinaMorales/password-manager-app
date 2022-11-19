package com.example.password_manager_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.theme.TopBarOpal

@Composable
fun TopBar(onNavIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.7F)
                ) {
                    val focusManager = LocalFocusManager.current
                    PasswordManagerTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text="Search") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, "")
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),

                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(.6F),
                    horizontalAlignment = Alignment.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.koalalogo),
                        contentDescription = ""
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onNavIconClick() }) {
                Icon(
                    tint = Color.Black,
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "navigation",
                )
            }
        },
        backgroundColor = TopBarOpal,
    )
}