package com.example.password_manager_app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(onAddSecretClick: () -> Unit, onLogOut: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val innerNav = rememberNavController()
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
            TopBar {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        floatingActionButton = { AddSecret(onAddSecretClick) },
        drawerContent = {
            NavigationDrawer(
                scaffoldState,
                coroutineScope,
                { innerNav.navigate("secrets") },
                { innerNav.navigate("profile") },
                { onLogOut() }
            )
        },
    ) {
        NavHost(navController = innerNav, startDestination = "secrets") {
            composable("secrets") {
                SecretsView()
            }
            composable("profile") {
                Profile()
            }
        }
    }
}

@Composable
private fun TopBar(onNavIconClick: () -> Unit) {
    TopAppBar(
        title = { Text("Secrets Secured") },
        navigationIcon = {
            IconButton(onClick = {
                onNavIconClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "navigation"
                )
            }
        }
    )
}

@Composable
private fun AddSecret(onClick: () -> Unit) {
    FloatingActionButton(onClick) {
        Icon(
            Icons.Default.Add,
            "Add Secret"
        )
    }
}

@Composable
fun NavigationDrawer(
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
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Hi, ",//eventually will include ${name
                fontSize = 20.sp
            )
            NavigationItem(icon = Icons.Default.List, text = "View Secrets") {
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
    }
}

@Composable
fun NavigationItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(top = 20.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon,"")
        Text(
            modifier = Modifier.padding(start = 10.dp),
            textAlign = TextAlign.Center,
            text = text,
            fontSize = 20.sp
        )
    }
}
