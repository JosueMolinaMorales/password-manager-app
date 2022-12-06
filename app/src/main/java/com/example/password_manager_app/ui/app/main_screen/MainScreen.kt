package com.example.password_manager_app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.data.PagesWithBottomSheet
import com.example.password_manager_app.data.User
import com.example.password_manager_app.ui.app.main_screen.MainScreenViewModel
import com.example.password_manager_app.ui.app.records.create_password.CreatePasswordPage
import com.example.password_manager_app.ui.app.records.create_secret.CreateSecretPage
import com.example.password_manager_app.ui.app.records.RecordsView
import com.example.password_manager_app.ui.components.NavigationDrawer
import com.example.password_manager_app.ui.theme.Charcoal
import com.example.password_manager_app.ui.theme.TopBarOpal
import kotlinx.coroutines.launch
import com.example.password_manager_app.ui.components.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    onLogOut: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val innerNav = rememberNavController()
    val focusManager = LocalFocusManager.current
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val showFAB = remember { mutableStateOf(true) }
    val currentPage = remember { mutableStateOf(PagesWithBottomSheet.HomePage) }
    val vm: MainScreenViewModel = viewModel()

    // TODO: Remove This Modal Bottom Sheet and implement one in every page that needs one
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
            TopBar {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        floatingActionButton = {
            AddSecretFAB(
                onClick = { coroutineScope.launch { bottomState.show() }},
                showFAB = showFAB.value
            )
        },
        drawerContent = {
            NavigationDrawer(
                user = vm.user.value,
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope,
                navToSecrets = { innerNav.navigate("records") },
                navToProfile = { innerNav.navigate("profile") },
                Logout = {
                    onLogOut()
                }
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
                showFAB.value = true
                currentPage.value = PagesWithBottomSheet.HomePage
            }
            composable("profile") {
                showFAB.value = false
                //currentPage.value = PagesWithBottomSheet.ProfilePage
                Profile(vm.user.value ?: User(),
                    onEditChange = { newUser ->
                        vm.updateUser(newUser)
                    },
                )
            }
            composable("createPassword") {
                CreatePasswordPage(
                    token = vm.user.value?.token ?: "",
                    onCreatePasswordClick = {
                        innerNav.navigate("records") {
                            popUpTo("records") { inclusive = true }
                        }
                    }
                )
                showFAB.value = false
            }
            composable("createSecret") {
                CreateSecretPage(
                    token = vm.user.value?.token ?: "",
                    onCreateSecret = {
                        innerNav.navigate("records") {
                            popUpTo("records")
                        }
                    }
                )
                showFAB.value = false
            }
        }
    }
}


@Composable
private fun AddSecretFAB(onClick: () -> Unit, showFAB: Boolean = true) {
    if (showFAB) {
        FloatingActionButton(
            onClick,
            backgroundColor = TopBarOpal
        ) {
            Icon(
                Icons.Default.Add,
                "Add Record",
                tint = Color.Black,
            )
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