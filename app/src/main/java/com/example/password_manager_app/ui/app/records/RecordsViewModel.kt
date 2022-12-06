package com.example.password_manager_app.ui.app.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.app.record.RecordNetwork
import kotlinx.coroutines.launch

class RecordsViewModel: ViewModel() {
    private val _isFetchingRecords: MutableState<Boolean> = mutableStateOf(false)
    val isFetchingRecords: State<Boolean> = _isFetchingRecords

    private val _passwordFilterIsCheck: MutableState<Boolean> = mutableStateOf(true)
    val passwordFilterIsCheck: State<Boolean> = _passwordFilterIsCheck

    private val _secretFilterIsCheck: MutableState<Boolean> = mutableStateOf(true)
    val secretFilterIsCheck: State<Boolean> = _secretFilterIsCheck

    private val _isMakingDeleteRequest: MutableState<Boolean> = mutableStateOf(false)
    val isMakingDeleteRequest: State<Boolean> = _isMakingDeleteRequest

    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _records: MutableState<List<Record>> = mutableStateOf(listOf())
    var records: State<List<Record>> = _records

    val recNet: RecordNetwork = RecordNetwork()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun togglePasswordFilter() {
        _passwordFilterIsCheck.value = !_passwordFilterIsCheck.value
    }

    fun toggleSecretFilter() {
        _secretFilterIsCheck.value = !_secretFilterIsCheck.value
    }

    suspend fun fetchRecords(token: String, userId: String) {
        _isFetchingRecords.value = true
       _records.value = recNet.fetchRecords(token, userId)
        _isFetchingRecords.value = false
    }

    fun deleteRecord(
        recordId: String,
        token: String,
        userId: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            _isMakingDeleteRequest.value = true
            val res = recNet.deleteRecord(token = token, recordId = recordId)
            _isMakingDeleteRequest.value = false
            when (res.code) {
                204 -> {
                    onSuccess()
                    fetchRecords(token = token, userId = userId)
                }
                else -> onError()
            }
        }
    }

    fun searchRecord(
        userId: String,
        token: String,
        query: String
    ) {
        viewModelScope.launch {
            _isFetchingRecords.value = true
            val res = recNet.searchRecord(
                token = token,
                userId = userId,
                query = query
            )
            _isFetchingRecords.value = false
            when (res.code) {
                200 -> {

                }
                else -> {}
            }
        }
    }
}