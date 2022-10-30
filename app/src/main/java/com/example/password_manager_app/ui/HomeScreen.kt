package com.example.password_manager_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.R

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(75.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Password Manager", fontSize = 25.sp)
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = ""
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = onNavigateToLogin) {
            Text("Login")
        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = onNavigateToRegister) {
            Text("Register")
        }
    }
}