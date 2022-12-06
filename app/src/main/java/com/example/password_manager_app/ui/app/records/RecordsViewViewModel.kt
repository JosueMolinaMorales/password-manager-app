package com.example.password_manager_app.ui.app.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.app.record.RecordNetwork
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RecordsViewViewModel: ViewModel() {
    private val _isFetchingRecords: MutableState<Boolean> = mutableStateOf(false)
    val isFetchingRecords: State<Boolean> = _isFetchingRecords

    private val _isMakingDeleteRequest: MutableState<Boolean> = mutableStateOf(false)
    val isMakingDeleteRequest: State<Boolean> = _isMakingDeleteRequest

    private val _records: MutableState<List<Record>> = mutableStateOf(listOf())
    var records: State<List<Record>> = _records

    private val recNet: RecordNetwork = RecordNetwork()

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
                    fetchRecords(token = token, userId = userId, {})
                }
                else -> onError()
            }
        }
    }
    
    suspend fun fetchRecords(token: String, userId: String, onUnsuccessfulLogin: (String) -> Unit) {
        val response = recNet.fetchRecords(token, userId)
        when (response.code) {
            200 -> {
                val gson = Gson()
                val listType: Type = object: TypeToken<List<Record>>() {}.type
                if(response.body != null) {
                    _records.value = gson.fromJson(response.body!!.string(), listType)
                } else {
                    _records.value = listOf()
                }
            }
            else -> {
                onUnsuccessfulLogin("Internal Service Error, Please Try Again Later")
            }
        }
    }
}