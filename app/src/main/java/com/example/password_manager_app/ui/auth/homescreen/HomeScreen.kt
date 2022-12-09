package com.example.password_manager_app.ui.auth.homescreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerButton

/**
 * The HomeScreen view
 */
@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Welcome!",
                fontSize = 48.sp,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.logo_dark_mode),
                modifier = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Modifier.fillMaxWidth(.5F).fillMaxHeight(.4F)
                } else {
                    Modifier.fillMaxWidth(.9F).fillMaxHeight(.6F)
               },
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
        when (LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PasswordManagerButton(
                        modifier = Modifier.fillMaxWidth(fraction = 0.7F),
                        onClick = onNavigateToLogin
                    ) {
                        Text("Login")
                    }
                    PasswordManagerButton(
                        modifier = Modifier.fillMaxWidth(fraction = 0.7F),
                        onClick = onNavigateToRegister
                    ) {
                        Text("Register")
                    }
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 64.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PasswordManagerButton(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Login")
                    }
                    PasswordManagerButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}