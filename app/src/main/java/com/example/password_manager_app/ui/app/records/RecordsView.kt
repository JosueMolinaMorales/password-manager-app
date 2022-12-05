package com.example.password_manager_app.ui.app.records

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.data.getRandomStreamingService
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.model.Record
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
    recordsViewViewModel: RecordsViewViewModel,
    mainScreenViewModel: MainScreenViewModel,
    clipboard: ClipboardManager
) {
    val showPasswordViewModel: ViewPasswordViewModel = viewModel()
    val showSecretViewModel: ViewSecretViewModel = viewModel()
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    ViewPassword(showPasswordViewModel)
    ViewSecret(showSecretViewModel)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        coroutineScope.launch {
            recordsViewViewModel.fetchRecords(
                mainScreenViewModel.user.value?.token!!,
                mainScreenViewModel.user.value?.id!!
            )
        }

        if(recordsViewViewModel.records.value.isEmpty()) {
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
        }else {
            LazyColumn(
                verticalArrangement = Arrangement.Top
            ) {
            itemsIndexed(recordsViewViewModel.records.value) { _, record ->
                var title = ""
                if (record.recordType == RecordType.Password){
                    title = record.service!!
                } else if (record.recordType == RecordType.Secret){
                    title = record.key!!
                }
                RecordRowView(
                    /* Should send the object for this record eventually not Str*/
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
                    onDeleteClick = {},
                    onEditClick = {},
                    title = title,
                    recordType = record.recordType
                )
            }
            }
        }
    }
}