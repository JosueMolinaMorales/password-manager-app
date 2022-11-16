package com.example.password_manager_app.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.R
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.Charcoal
import com.example.password_manager_app.ui.theme.TopBarOpal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(onAddRecordClick: () -> Unit, onLogOut: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val innerNav = rememberNavController()
    val focusManager = LocalFocusManager.current
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
            TopBar {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        floatingActionButton = { AddSecretFAB(onAddRecordClick) },
        drawerContent = {
            NavigationDrawer(
                scaffoldState,
                coroutineScope,
                { innerNav.navigate("records") },
                { innerNav.navigate("profile") },
                { onLogOut() }
            )
        },
        drawerBackgroundColor = Charcoal,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) {
        NavHost(navController = innerNav, startDestination = "records") {
            composable("records") {
                RecordsView()
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
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val focusManager = LocalFocusManager.current
                PasswordManagerTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text="Search") },
                    modifier = Modifier
                        .fillMaxWidth(.8F)
                        .scale(.9F),
                    leadingIcon = {
                        Icon(Icons.Default.Search, "")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                )
                Image(
                    painter = painterResource(id = R.drawable.koalalogo),
                    contentDescription = ""
                )
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

@Composable
private fun AddSecretFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick, backgroundColor = TopBarOpal) {
        Icon(
            Icons.Default.Add,
            "Add Record",
            tint = Color.Black
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
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    text = "Hi, User!",//eventually will include ${name
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000),
                    textAlign = TextAlign.Center
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
            text = text,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(1000)
        )
    }
}
