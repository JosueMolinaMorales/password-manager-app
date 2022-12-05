package com.example.password_manager_app.ui.app.records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.app.record.RecordNetwork
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RecordsViewViewModel: ViewModel() {
    private val _records: MutableState<List<Record>> = mutableStateOf(listOf())
    var records: State<List<Record>> = _records

    val recNet: RecordNetwork = RecordNetwork()

    suspend fun fetchRecords(token: String, userId: String) {
       val response = recNet.fetchRecords(token, userId)
        val gson = Gson()
        val listType: Type = object: TypeToken<List<Record>>() {}.type
        if(response.body != null) {
            _records.value = gson.fromJson(response.body!!.string(), listType)
        } else {
            _records.value = listOf()
        }
    }
}