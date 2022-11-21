package com.example.password_manager_app.ui.app.records.view_records

<<<<<<< HEAD
import android.text.style.ClickableSpan
import android.widget.ImageButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
=======
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
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.ui.components.OutlinedPasswordManagerButton
import com.example.password_manager_app.ui.theme.PewterBlue
<<<<<<< HEAD
import com.example.password_manager_app.R
=======
import com.example.password_manager_app.ui.components.PasswordManagerTextField
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0

@Composable
fun ViewPassword(
    ViewPasswordViewModel: ViewPasswordViewModel
) {
<<<<<<< HEAD
=======
    val showPassword: MutableState<Boolean> = remember { mutableStateOf(false) }
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
    val show by ViewPasswordViewModel.show
    if(show){
        AlertDialog(
            backgroundColor = PewterBlue,
            modifier = Modifier
                .fillMaxWidth()
<<<<<<< HEAD
                .height(350.dp),
            onDismissRequest = { ViewPasswordViewModel.hide() },
            title = {

            },
=======
                .height(400.dp),
            onDismissRequest = { ViewPasswordViewModel.hide() },
            title = {},
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            text = ViewPasswordViewModel.title.value
                        )
<<<<<<< HEAD

=======
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
<<<<<<< HEAD
                            Column() {
                                Text(text = "Username", color = Color.Black, fontSize = 20.sp)
                                Text(
                                    //TODO change to get value from ViewPasswordViewModel
                                    text = "MyUsername",
                                    fontSize = 30.sp,
                                    color = Color.Black
=======
                            Column {
                                //TODO change to get value from ViewPasswordViewModel
                                PasswordManagerTextField(
                                    value = "MyUsername",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(text = "Login") },
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
<<<<<<< HEAD
                            Column() {
                                Text(text = "Username", color = Color.Black, fontSize = 20.sp)
                                //TODO create composable to hide and unhide pword
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    ){
                                    Text(
                                        text = "********",
                                        fontSize = 30.sp,
                                        color = Color.Black
                                    )
                                    IconButton(onClick = {/*TODO*/}) {
                                        Image(
                                            painter =  painterResource(id = R.drawable.closedeye),
                                            contentDescription = "Closed Eye"
                                        )
                                    }

                                }
=======
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
>>>>>>> 68d3a552d39c294e9d22a2d41d81b552af81acf0
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
