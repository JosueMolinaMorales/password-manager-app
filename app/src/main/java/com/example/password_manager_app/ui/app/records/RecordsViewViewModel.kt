package com.example.password_manager_app.ui.app.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.app.record.RecordNetwork

class RecordsViewViewModel: ViewModel() {
    private val _isFetchingRecords: MutableState<Boolean> = mutableStateOf(false)
    val isFetchingRecords: State<Boolean> = _isFetchingRecords

    private val _records: MutableState<List<Record>> = mutableStateOf(listOf())
    var records: State<List<Record>> = _records

    val recNet: RecordNetwork = RecordNetwork()

    suspend fun fetchRecords(token: String, userId: String) {
        _isFetchingRecords.value = true
       _records.value = recNet.fetchRecords(token, userId)
        _isFetchingRecords.value = false
    }
}