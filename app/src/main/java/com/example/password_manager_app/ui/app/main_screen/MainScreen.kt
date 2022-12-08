package com.example.password_manager_app.ui.app.main_screen

import android.annotation.SuppressLint
import android.content.ClipboardManager
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.password_manager_app.model.User
import com.example.password_manager_app.model.ActionOnRecord
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.Profile
import com.example.password_manager_app.ui.app.records.create_update_secret.CreateSecretPage
import com.example.password_manager_app.ui.app.records.RecordsView
import com.example.password_manager_app.ui.app.records.create_update_password.CreateUpdatePasswordPage
import com.example.password_manager_app.ui.app.records.RecordsViewModel
import com.example.password_manager_app.ui.app.records.components.create_record.CreateRecordView
import com.example.password_manager_app.ui.components.NavigationDrawer
import com.example.password_manager_app.ui.theme.Charcoal
import com.example.password_manager_app.ui.theme.TopBarOpal
import kotlinx.coroutines.launch
import com.example.password_manager_app.ui.components.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    onLogOut: () -> Unit,
    clipboard: ClipboardManager
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val innerNav = rememberNavController()
    val focusManager = LocalFocusManager.current
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val showFAB = remember { mutableStateOf(true) }
    val vm: MainScreenViewModel = viewModel()
    val recordsViewModel: RecordsViewModel = viewModel()

    if(vm.user.value == null) {
        CircularProgressIndicator()
    }else {
        ModalBottomSheetLayout(
            sheetContent = {
                CreateRecordView(
                    onCreatePasswordClick = {
                        innerNav.navigate("createPassword/create/new")
                        coroutineScope.launch {
                            bottomState.hide()
                        }
                    },
                    onCreateSecretClick = {
                        innerNav.navigate("createSecret/create/new")
                        coroutineScope.launch {
                            bottomState.hide()
                        }
                    }
                )
            },
            sheetState = bottomState,
            sheetBackgroundColor = Charcoal,
            scrimColor = Charcoal.copy(alpha=.7F)
        ) {
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
                        RecordsView(
                            onEditClick = { recordType, recordId ->
                                if (recordType == RecordType.Secret) {
                                    innerNav.navigate("createSecret/update/$recordId")
                                } else {
                                    innerNav.navigate("createPassword/update/$recordId")
                                }
                            },
                            recordsViewModel = recordsViewModel,
                            mainScreenViewModel = vm,
                            clipboard = clipboard
                        )
                        showFAB.value = true
                    }
                    composable("profile") {
                        Profile(
                            user = vm.user.value ?: User(),
                            onEditChange = { newUser ->
                                vm.updateUser(newUser)
                            }
                        )
                        showFAB.value = false
                    }
                    composable(
                        route = "createPassword/{action}/{recordId}",
                        arguments = listOf(
                            navArgument("action") { type =  NavType.StringType },
                            navArgument("recordId") { type = NavType.StringType }
                        )
                    ) { navBackStack ->
                        CreateUpdatePasswordPage(
                            token = vm.user.value?.token ?: "",
                            onNavigateHome = {
                                innerNav.navigate("records") {
                                    popUpTo("records") { inclusive = true }
                                }
                            },
                            action = if (navBackStack.arguments?.getString("action") == ActionOnRecord.Create.value) {
                                ActionOnRecord.Create
                            } else {
                                ActionOnRecord.Update
                            },
                            recordId = navBackStack.arguments?.getString("recordId")
                        )
                        showFAB.value = false
                    }
                    composable(
                        route = "createSecret/{action}/{recordId}",
                        arguments = listOf(
                            navArgument("action") { type =  NavType.StringType },
                            navArgument("recordId") { type = NavType.StringType }
                        )
                    ) { navBackStack ->
                        CreateSecretPage(
                            token = vm.user.value?.token ?: "",
                            onNavigateHome = {
                                innerNav.navigate("records") {
                                    popUpTo("records") { inclusive = true }
                                }
                            },
                            action = if (navBackStack.arguments?.getString("action") == ActionOnRecord.Create.value) {
                                ActionOnRecord.Create
                            } else {
                                ActionOnRecord.Update
                            },
                            recordId = navBackStack.arguments?.getString("recordId")
                        )
                        showFAB.value = false
                    }
                }
            }
        }

    }
    // TODO: Remove This Modal Bottom Sheet and implement one in every page that needs one

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