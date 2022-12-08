package com.example.password_manager_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.R
import com.example.password_manager_app.model.User
import com.example.password_manager_app.ui.app.main_screen.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    user: User?,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    navToSecrets: () -> Unit,
    navToProfile: () -> Unit,
    Logout: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    text = "Hi, " + if (user == null) { "User!" } else { user.name + "!" },
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000),
                    textAlign = TextAlign.Center
                )
                NavigationItem(icon = Icons.Default.List, text = "View Records") {
                    navToSecrets()
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
                NavigationItem(icon = Icons.Default.AccountBox, text = "View Profile") {
                    navToProfile()
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
                NavigationItem(icon = Icons.Default.ExitToApp, text = "Logout") {
                    Logout()
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_dark_mode),
                    contentDescription = ""
                )
            }
        }
    }
}