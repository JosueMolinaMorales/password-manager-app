package com.example.password_manager_app.ui.app.records

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.app.main_screen.MainScreenViewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewPassword
import com.example.password_manager_app.ui.app.records.view_records.ViewPasswordViewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewSecret
import com.example.password_manager_app.ui.app.records.view_records.ViewSecretViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecordsView(
    onEditClick: (RecordType, String) -> Unit,
    recordsViewModel: RecordsViewViewModel,
    mainScreenViewModel: MainScreenViewModel,
    clipboard: ClipboardManager
) {
    val showPasswordViewModel: ViewPasswordViewModel = viewModel()
    val showSecretViewModel: ViewSecretViewModel = viewModel()
    ViewPassword(
        vm = showPasswordViewModel,
        onEditClick = onEditClick,
        onDeleteClick = {
            recordsViewModel.deleteRecord(
                recordId = showPasswordViewModel.record.value?.id!!,
                token = mainScreenViewModel.user.value?.token!!,
                userId = mainScreenViewModel.user.value?.id!!,
                onSuccess = { showPasswordViewModel.hide() },
                onError = {}
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
                onError = {}
            )
        }
    )
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        LaunchedEffect(key1 = recordsViewModel.records.value,){
            recordsViewModel.fetchRecords(
                mainScreenViewModel.user.value?.token!!,
                mainScreenViewModel.user.value?.id!!
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