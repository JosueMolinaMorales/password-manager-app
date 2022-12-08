package com.example.password_manager_app.ui.app.records

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.Filter
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.ManageSearch
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.app.main_screen.MainScreenViewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewPassword
import com.example.password_manager_app.ui.app.records.view_records.ViewPasswordViewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewSecret
import com.example.password_manager_app.ui.app.records.view_records.ViewSecretViewModel
import com.example.password_manager_app.ui.components.PasswordManagerTextField
import com.example.password_manager_app.ui.theme.Opal
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.components.PasswordManagerSnackbar
import kotlinx.coroutines.delay


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecordsView(
    onEditClick: (RecordType, String) -> Unit,
    recordsViewModel: RecordsViewModel,
    mainScreenViewModel: MainScreenViewModel,
    clipboard: ClipboardManager
) {
    val showPasswordViewModel: ViewPasswordViewModel = viewModel()
    val showSecretViewModel: ViewSecretViewModel = viewModel()
    val showFilterDropDown: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val errorMsg: MutableState<String?> = remember { mutableStateOf(null) }

    ViewPassword(
        vm = showPasswordViewModel,
        onEditClick = onEditClick,
        onDeleteClick = {
            recordsViewModel.deleteRecord(
                recordId = showPasswordViewModel.record.value?.id!!,
                token = mainScreenViewModel.user.value?.token!!,
                userId = mainScreenViewModel.user.value?.id!!,
                onSuccess = { showPasswordViewModel.hide() },
                onError = { msg ->
                    errorMsg.value = msg
                }
            )
        }
    )
    ViewSecret(
        vm = showSecretViewModel,
        onEditClick = onEditClick,
        onDeleteClick = {
            recordsViewModel.deleteRecord(
                recordId = showSecretViewModel.record.value?.id!!,
                token = mainScreenViewModel.user.value?.token!!,
                userId = mainScreenViewModel.user.value?.id!!,
                onSuccess = { showSecretViewModel.hide() },
                onError = { msg ->
                    errorMsg.value = msg
                }
            )
        }
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PasswordManagerTextField(
                value = recordsViewModel.searchQuery.value,
                onValueChange = { query ->
                    recordsViewModel.searchRecord(
                        query = query,
                        userId = mainScreenViewModel.user.value?.id!!,
                        token = mainScreenViewModel.user.value?.token!!,
                        onError = {
                            errorMsg.value = it
                        }
                    )
                },
                trailingIcon = { Image(Icons.Outlined.Search, contentDescription = "") },
                modifier = Modifier.fillMaxWidth(.85F),
                placeholder = { Text(text = "Search Records...") }
            )
            Column {
                IconButton(
                    onClick = { showFilterDropDown.value = !showFilterDropDown.value }
                ) {
                    Image(
                        Icons.Outlined.FilterList,
                        modifier = Modifier.size(30.dp),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Opal),
                        contentScale = ContentScale.FillBounds
                    )
                }
                DropdownMenu(
                    expanded = showFilterDropDown.value,
                    onDismissRequest = { showFilterDropDown.value = false },
                ) {
                    DropdownMenuItem(onClick = {
                        recordsViewModel.toggleFilter(
                            recordType = RecordType.Secret,
                            query = recordsViewModel.searchQuery.value,
                            userId = mainScreenViewModel.user.value?.id!!,
                            token = mainScreenViewModel.user.value?.token!!,
                            onError = {
                                errorMsg.value = it
                            }
                        )
                    }) {
                        if (recordsViewModel.secretFilterIsCheck.value) {
                            Image(Icons.Default.CheckBox, contentDescription = "")
                        } else {
                            Image(Icons.Default.CheckBoxOutlineBlank, contentDescription = "")
                        }
                        Text(text = "Secrets", modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    DropdownMenuItem(onClick = {
                        recordsViewModel.toggleFilter(
                            recordType = RecordType.Password,
                            query = recordsViewModel.searchQuery.value,
                            userId = mainScreenViewModel.user.value?.id!!,
                            token = mainScreenViewModel.user.value?.token!!,
                            onError = {
                                errorMsg.value = it
                            }
                        )
                    }) {
                        if (recordsViewModel.passwordFilterIsCheck.value) {
                            Image(Icons.Default.CheckBox, contentDescription = "")
                        } else {
                            Image(Icons.Default.CheckBoxOutlineBlank, contentDescription = "")
                        }
                        Text(text = "Passwords", modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
            }
        }
        Divider()
        if (errorMsg.value != null) {
            PasswordManagerSnackbar(
                modifier = Modifier.padding(8.dp).zIndex(1F),
                action = {
                    PasswordManagerButton(onClick = { errorMsg.value = null }) {
                        Text(text = "Confirm")
                    }
                }
            ) {
                Text(text = errorMsg.value ?: "An Error Occurred")
                LaunchedEffect(key1 = errorMsg) {
                    delay(5000)
                    errorMsg.value = null
                }
            }
        }
        RecordList(
            onEditClick = onEditClick,
            recordsViewModel = recordsViewModel,
            mainScreenViewModel = mainScreenViewModel,
            clipboard = clipboard,
            showPasswordViewModel = showPasswordViewModel,
            showSecretViewModel = showSecretViewModel,
            errorMsg = errorMsg
        )
    }
}

@Composable
fun RecordList(
    onEditClick: (RecordType, String) -> Unit,
    recordsViewModel: RecordsViewModel,
    mainScreenViewModel: MainScreenViewModel,
    showPasswordViewModel: ViewPasswordViewModel,
    showSecretViewModel: ViewSecretViewModel,
    errorMsg: MutableState<String?>,
    clipboard: ClipboardManager
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        LaunchedEffect(key1 = true,){
            recordsViewModel.fetchRecords(
                mainScreenViewModel.user.value?.token!!,
                mainScreenViewModel.user.value?.id!!,
                onError = { msg ->
                    errorMsg.value = msg
                }
            )
        }

        if(recordsViewModel.records.value.isEmpty() && recordsViewModel.isFetchingRecords.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        } else if (recordsViewModel.records.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Records Found!",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(recordsViewModel.records.value) { _, record ->
                    var title = ""
                    if (record.recordType == RecordType.Password){
                        title = record.service!!
                    } else if (record.recordType == RecordType.Secret){
                        title = record.key!!
                    }
                    RecordRowView(
                        onCardClick = {
                            if (record.recordType == RecordType.Password) {
                                showPasswordViewModel.show(record)
                            } else {
                                showSecretViewModel.show(record)
                            }},
                        onCopyToClipboardClick = {
                            val clipData: ClipData? = if(record.recordType == RecordType.Secret) {
                                ClipData.newPlainText("secret", record.secret)
                            } else {
                                ClipData.newPlainText("password", record.password)
                            }
                            clipboard.setPrimaryClip(clipData!!)
                            // Enable the snackbar to show that the content was copied
                            errorMsg.value = "Copied!"
                        },
                        onDeleteClick = {
                            recordsViewModel.deleteRecord(
                                recordId = record.id!!,
                                token = mainScreenViewModel.user.value?.token!!,
                                userId = mainScreenViewModel.user.value?.id!!,
                                onError = {},
                                onSuccess = {
                                    if (record.recordType == RecordType.Password) {
                                        showPasswordViewModel.hide()
                                    } else {
                                        showSecretViewModel.hide()
                                    }
                                }
                            )
                        },
                        onEditClick = { recordType ->
                            onEditClick(recordType, record.id!!)
                        },
                        title = title,
                        recordType = record.recordType
                    )
                }
            }
        }

    }
}