package com.example.password_manager_app.ui.app.records

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.password_manager_app.data.RecordType
import com.example.password_manager_app.data.getRandomStreamingService
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewPassword
import com.example.password_manager_app.ui.app.records.view_records.ViewPasswordViewModel
import com.example.password_manager_app.ui.app.records.view_records.ViewSecret
import com.example.password_manager_app.ui.app.records.view_records.ViewSecretViewModel


@Composable
fun RecordsView() {
    val showPasswordViewModel: ViewPasswordViewModel = viewModel()
    val showSecretViewModel: ViewSecretViewModel = viewModel()
    ViewPassword(showPasswordViewModel)
    ViewSecret(showSecretViewModel)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val aList = (1..20).toList()
        LazyColumn {
            itemsIndexed(aList) { idx, str ->
                val title = getRandomStreamingService()
                RecordRowView(
                    /* Should send the object for this record eventually not Str*/
                    onCardClick = {
                        if (idx % 2 == 0) {
                            showPasswordViewModel.show(title)
                        } else {
                            showSecretViewModel.show(title)
                        }},
                    onCopyToClipboardClick = {},
                    onDeleteClick = {},
                    onEditClick = {},
                    title = title,
                    recordType = if (idx % 2 == 0) {
                        RecordType.Password
                    } else {
                        RecordType.Secret
                    }
                )
            }
        }
    }
}