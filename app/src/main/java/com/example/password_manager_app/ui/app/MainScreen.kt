package com.example.password_manager_app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.ui.components.NavigationDrawer
import com.example.password_manager_app.ui.theme.Charcoal
import com.example.password_manager_app.ui.theme.TopBarOpal
import kotlinx.coroutines.launch
import com.example.password_manager_app.ui.components.TopBar

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
