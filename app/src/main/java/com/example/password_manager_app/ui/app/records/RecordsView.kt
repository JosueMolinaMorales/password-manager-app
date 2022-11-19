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
import com.example.password_manager_app.data.getRandomStreamingService
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView
import com.example.password_manager_app.ui.app.records.view_records.ViewPassword
import com.example.password_manager_app.ui.app.records.view_records.ViewPasswordViewModel

@Composable
fun RecordsView() {
    val showViewModel: ViewPasswordViewModel = viewModel()
    ViewPassword(
        showViewModel
    )
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val aList = (1..20).map { i -> "Hi $i" }
        LazyColumn {
            itemsIndexed(aList) { idx, str ->
                RecordRowView(

                    /* Should send the object for this record eventually not Str*/
                    onCardClick = { showViewModel.show(str) },
                    onCopyToClipboardClick = {},
                    onDeleteClick = {},
                    onEditClick = {},
                    title = getRandomStreamingService(),
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