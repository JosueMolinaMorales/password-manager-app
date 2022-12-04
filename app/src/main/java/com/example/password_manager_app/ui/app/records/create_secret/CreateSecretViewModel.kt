package com.example.password_manager_app.ui.app.records.create_secret

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.network.ErrorResponse
import com.example.password_manager_app.network.app.record.RecordNetwork
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CreateSecretViewModel: ViewModel() {
    private val _key: MutableState<String> = mutableStateOf("")
    val key: State<String> = _key

    private val _secret: MutableState<String> = mutableStateOf("")
    val secret: State<String> = _secret

    private val _secretHasError: MutableState<Boolean> = mutableStateOf(false)
    val secretHasError: State<Boolean> = _secretHasError

    private val _keyHasError: MutableState<Boolean> = mutableStateOf(false)
    val keyHasError: State<Boolean> = _keyHasError

    private val _isMakingRequest: MutableState<Boolean> = mutableStateOf(false)
    val isMakingRequest: State<Boolean> = _isMakingRequest

    private val _recordNetwork = RecordNetwork()

    fun setKey(key: String) {
        _key.value = key
        _keyHasError.value = _key.value == ""
    }

    fun setSecret(secret: String) {
        _secret.value = secret
        _secretHasError.value = _secret.value == ""
    }

    fun validate(): String? {
        if (_key.value == "" && _secret.value == "") {
            // Both fields are empty
            _secretHasError.value = true
            _keyHasError.value = true
            return "Key and Secret are required"
        }
        if (_key.value == "") {
            _keyHasError.value = true
            return "Key is required"
        }
        if (_secret.value == "") {
            _secretHasError.value = true
            return "Secret is required"
        }
        return null
    }

    fun createSecret(
        token: String,
        onSuccessfulSecretCreate: () -> Unit,
        onUnsuccessfulCreate: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isMakingRequest.value = true
            val res = _recordNetwork.createRecord(Record(
                recordType = RecordType.Secret,
                key = _key.value,
                secret = _secret.value
            ), token)
            _isMakingRequest.value = false
            val body = res.body
            when (res.code) {
                200, 201 -> {
                    // Res body contains id of new record, could be stored in database
                    onSuccessfulSecretCreate()
                }
                400 -> {
                    val errBody = Gson().fromJson(body?.string(), ErrorResponse::class.java)
                    onUnsuccessfulCreate(errBody.error.message)
                }
                else -> {
                    onUnsuccessfulCreate("Internal Service Error, Please Try Again Later")
                }
            }

        }
    }
}