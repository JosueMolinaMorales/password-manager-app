package com.example.password_manager_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.password_manager_app.data.RecordType
import com.example.password_manager_app.data.getRandomStreamingService
import com.example.password_manager_app.ui.app.records.record_row.RecordRowView

@Composable
fun RecordsView() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val aList = (1..20).map { i -> "Hi $i" }
        LazyColumn {
            itemsIndexed(aList) { idx, str ->
                RecordRowView(
                    title = getRandomStreamingService(),
                    recordType = if (idx % 2 == 0) { RecordType.Password } else { RecordType.Secret },
                    onCardClick = {},
                    onCopyToClipboardClick = {},
                    onDeleteClick = {},
                    onEditClick = {}
                )
            }
        }
    }
}