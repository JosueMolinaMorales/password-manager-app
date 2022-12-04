package com.example.password_manager_app.ui.app.records.view_records

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.components.OutlinedPasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.PewterBlue


@Composable
fun ViewPassword(
    ViewPasswordViewModel: ViewPasswordViewModel,
    onEditClick: (RecordType) -> Unit
) {
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(false) }
    val show by ViewPasswordViewModel.show
    if(show){
        AlertDialog(
            backgroundColor = PewterBlue,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7F),
            onDismissRequest = { ViewPasswordViewModel.hide() },
            title = {},
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
                            text = ViewPasswordViewModel.title.value,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
                            Column {
                                //TODO change to get value from ViewPasswordViewModel
                                PasswordManagerTextField(
                                    value = "MyUsername",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(text = "Login") },
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
                            Column {
                                PasswordManagerTextField(
                                    value = "MyPassword",
                                    onValueChange = { },
                                    readOnly = true,
                                    label = { Text(text = "Password") },
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
                                    hideText = !showPassword.value
                                )
                            }
                        }
                    }
                }
            },
            buttons = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedPasswordManagerButton(
                        modifier = Modifier.width(200.dp),
                        onClick = { onEditClick(RecordType.Password) },
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = "Edit", fontSize = 15.sp)
                    }
                    OutlinedPasswordManagerButton(
                        modifier = Modifier.width(200.dp),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = "Delete", fontSize = 15.sp)
                    }
                }
            }
        )
    }
}
