package com.example.password_manager_app.ui.app.records

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.network.app.record.RecordNetwork
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RecordsViewModel(app: Application): AndroidViewModel(app) {
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

    private val ctx = getApplication<Application>()
    private val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val recNet: RecordNetwork = RecordNetwork(connectivityManager)

    fun toggleFilter(
        recordType: RecordType,
        userId: String,
        token: String,
        query: String,
        onError: (String) -> Unit
    ) {
        if (recordType == RecordType.Password) {
            _passwordFilterIsCheck.value = !_passwordFilterIsCheck.value
        } else {
            _secretFilterIsCheck.value = !_secretFilterIsCheck.value
        }
        searchRecord(
            userId = userId,
            token = token,
            query = query,
            onError = onError
        )
    }

    fun deleteRecord(
        recordId: String,
        token: String,
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isMakingDeleteRequest.value = true
            val res = recNet.deleteRecord(token = token, recordId = recordId)
            _isMakingDeleteRequest.value = false
            if (res != null) {
                when (res.code) {
                    204 -> {
                        onSuccess()
                        fetchRecords(token = token, userId = userId, {})
                    }
                    else -> onError("Internal Service Error")
                }
            } else {
                onError("Not connected to the network")
            }
        }
    }


    fun searchRecord(
        userId: String,
        token: String,
        query: String,
        onError: (String) -> Unit
    ) {
        _searchQuery.value = query
        viewModelScope.launch {
            _isFetchingRecords.value = true
            val res = recNet.searchRecord(
                token = token,
                userId = userId,
                query = query
            )
            _isFetchingRecords.value = false
            if (res != null) {
                when (res.code) {
                    200 -> {
                        val gson = Gson()
                        val listType: Type = object: TypeToken<List<Record>>() {}.type
                        if(res.body != null) {
                            _records.value = gson.fromJson(res.body!!.string(), listType)
                            // filter
                            if (!_passwordFilterIsCheck.value) {
                                // Remove password
                                _records.value = _records.value.filter { record ->
                                    record.recordType != RecordType.Password
                                }
                            }
                            if (!_secretFilterIsCheck.value) {
                                // Remove Secret
                                _records.value = _records.value.filter { record ->
                                    record.recordType != RecordType.Secret
                                }
                            }
                        } else {
                            _records.value = listOf()
                        }
                    }
                    else -> {
                        onError("An Error Occurred Fetching Your Records. Please Try Again Later.")
                    }
                }
            } else {
                onError("Not connected to the network")
            }

        }
    }

    suspend fun fetchRecords(token: String, userId: String, onError: (String) -> Unit) {
        val response = recNet.fetchRecords(token, userId)
        if(response != null) {
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
                    onError("Internal Service Error, Please Try Again Later")
                }
            }
        } else {
            onError("Not Connected to the network")
        }
    }
    
}