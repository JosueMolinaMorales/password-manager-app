package com.example.password_manager_app.ui.app.records.view_records

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.ui.components.OutlinedPasswordManagerButton
import com.example.password_manager_app.ui.theme.PewterBlue
import com.example.password_manager_app.R

@Composable
fun ViewPassword(
    ViewPasswordViewModel: ViewPasswordViewModel
) {
    val show by ViewPasswordViewModel.show
    if(show){
        AlertDialog(
            backgroundColor = PewterBlue,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            onDismissRequest = { ViewPasswordViewModel.hide() },
            title = {

            },
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

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
                            Column() {
                                Text(text = "Username", color = Color.Black, fontSize = 20.sp)
                                Text(
                                    //TODO change to get value from ViewPasswordViewModel
                                    text = "MyUsername",
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                            ) {
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
