package com.example.password_manager_app.ui.app.records.view_records

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.R
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.components.OutlinedPasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.PewterBlue

/**
 * The view for a secret record
 */
@Composable
fun ViewSecret(
    vm: ViewSecretViewModel,
    onEditClick: (RecordType, String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val show by vm.show

    if (show) {
        AlertDialog(
            backgroundColor = PewterBlue,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) { .8F } else { .6F }),
            onDismissRequest = { vm.hide() },
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
                            text = vm.record.value?.key!!,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            PasswordManagerTextField(
                                value = vm.record.value?.secret!!,
                                onValueChange = { },
                                readOnly = true,
                                enabled = false,
                                label = { Text(text = "Secret") },
                                trailingIcon = { IconToggleButton(
                                    checked = vm.showSecretValue.value,
                                    onCheckedChange = vm::toggleShowSecretValue
                                ) {
                                    if (vm.showSecretValue.value) {
                                        Icon(Icons.Filled.Visibility, "")
                                    } else {
                                        Icon(Icons.Filled.VisibilityOff, "")
                                    }
                                }
                                },
                                hideText = !vm.showSecretValue.value
                            )
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
                        onClick = { onEditClick(RecordType.Secret, vm.record.value?.id ?: "none") },
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = "Edit", fontSize = 15.sp)
                    }
                    OutlinedPasswordManagerButton(
                        modifier = Modifier.width(200.dp),
                        onClick = onDeleteClick,
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = "Delete", fontSize = 15.sp)
                    }
                }
            }
        )
    }
}