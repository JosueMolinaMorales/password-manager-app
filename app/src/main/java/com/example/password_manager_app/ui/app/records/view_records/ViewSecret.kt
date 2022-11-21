package com.example.password_manager_app.ui.app.records.view_records

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.OutlinedPasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.PewterBlue

@Composable
fun ViewSecret(
    viewSecretViewModel: ViewSecretViewModel
) {
    val show by viewSecretViewModel.show
    if (show) {
        AlertDialog(
            backgroundColor = PewterBlue,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7F),
            onDismissRequest = { viewSecretViewModel.hide() },
            title = {},
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            style = MaterialTheme.typography.h4,
                            text = viewSecretViewModel.title.value
                        )
                        Text(
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            style = MaterialTheme.typography.h6,
                            text = "Secret Record"
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Secret", color = Color.Black, fontSize = 20.sp)
                        //TODO create composable to hide and unhide pword
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            PasswordManagerTextField(
                                value = "Hello There",
                                readOnly = true,
                                enabled = false,
                                hideText = true,
                                onValueChange = {}
                            )
                        }
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().fillMaxHeight(.1F))

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
                        onClick = { /*TODO*/ },
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