package com.example.password_manager_app.ui.app.records.create_update_password

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
import com.example.password_manager_app.model.UpdateRecord
import com.example.password_manager_app.network.ErrorResponse
import com.example.password_manager_app.network.HttpCodes
import com.example.password_manager_app.network.app.record.RecordNetwork
import com.google.gson.Gson
import kotlinx.coroutines.launch

/**
 * The ViewModel for creating/updating a password record
 */
class CreateUpdatePasswordViewModel(app: Application): AndroidViewModel(app) {

    private val _service: MutableState<String> = mutableStateOf("")
    val service: State<String> = _service

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> = _username

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> = _password

    private val _serviceHasError: MutableState<Boolean> = mutableStateOf(false)
    val serviceHasError: State<Boolean> = _serviceHasError

    private val _emailHasError: MutableState<Boolean> = mutableStateOf(false)
    val emailHasError: State<Boolean> = _emailHasError

    private val _usernameHasError: MutableState<Boolean> = mutableStateOf(false)
    val usernameHasError: State<Boolean> = _usernameHasError

    private val _passwordHasError: MutableState<Boolean> = mutableStateOf(false)
    val passwordHasError: State<Boolean> = _passwordHasError

    private val _isMakingRequest: MutableState<Boolean> = mutableStateOf(false)
    val isMakingRequest: State<Boolean> = _isMakingRequest

    private val _record: MutableState<Record?> = mutableStateOf(null)

    private val ctx = getApplication<Application>()
    private val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val recordNet = RecordNetwork(connectivityManager)

    fun setService(service: String) {
        _service.value = service
        _serviceHasError.value = _service.value == ""
    }

    fun setEmail(email: String) {
        _email.value = email
        _emailHasError.value = emailHasError.value && _email.value == ""
    }

    fun setUsername(username: String) {
        _username.value = username
        _usernameHasError.value = usernameHasError.value && _username.value == ""
    }

    fun setPassword(password: String) {
        _password.value = password
        _passwordHasError.value = _password.value == ""
    }

    /**
     * Validates all the fields
     */
    fun validate(): String? {
        // Required Fields
        if (_service.value == "" && _password.value == "") {
            _serviceHasError.value = true
            _passwordHasError.value = true
            return "Service and Password are required"
        }
        if (_service.value == "") {
            _serviceHasError.value = true
            return "Service is required"
        }
        if (_password.value == "") {
            _passwordHasError.value = true
            return "Password is required"
        }

        // Either Email or Username need to be present
        if (_email.value == "" && _username.value == "") {
            return "Either email or username is required"
        }
        return null
    }

    /**
     * Calls the api to create the password
     */
    fun createPassword(
        token: String,
        onSuccessfulCreation: () -> Unit,
        onUnsuccessfulCreation: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isMakingRequest.value = true
            val res = recordNet.createRecord(
                record= Record(
                    password=_password.value,
                    email = if (_email.value == "") { null } else { _email.value.trim() },
                    username = if (_username.value == "") { null } else { _username.value.trim() },
                    service = _service.value.trim(),
                    recordType = RecordType.Password
                ),
                token = token
            )
            _isMakingRequest.value = false
            if(res!=null) {
                when (res.code) {
                    HttpCodes.Ok.code, HttpCodes.Created.code -> {
                        onSuccessfulCreation()
                    }
                    HttpCodes.BadRequest.code -> {
                        val error = Gson().fromJson(res.body?.string(), ErrorResponse::class.java)
                        onUnsuccessfulCreation(error.error.message)
                    }
                    else -> {
                        onUnsuccessfulCreation("Internal Service Error, Please Try Again Later.")
                    }
                }
            } else {
                onUnsuccessfulCreation("Not connected to the network")
            }
        }
    }

    /**
     * Gets a record from the api, if they are attempting to update it
     */
    fun getRecord(
        recordId: String?,
        token: String,
        onError: (String) -> Unit,
        onNotFound: () -> Unit
    ) {
        if (recordId == null) {
            onNotFound()
            return
        }
        viewModelScope.launch {
            _isMakingRequest.value = true
            val res = recordNet.getRecord(
                recordId = recordId,
                token = token
            )
            _isMakingRequest.value = false
            if (res != null) {
                when (res.code) {
                    HttpCodes.Ok.code -> {
                        val body = res.body?.string()
                        _record.value = Gson().fromJson(body, Record::class.java)
                        _username.value = _record.value?.username ?: ""
                        _email.value = _record.value?.email ?: ""
                        _password.value = _record.value?.password ?: ""
                        _service.value = _record.value?.service ?: ""
                    }
                    HttpCodes.NotFound.code -> {
                        onNotFound()
                    }
                    else -> {
                        val body = res.body?.string()
                        val errorRes = Gson().fromJson(body, ErrorResponse::class.java)
                        onError(errorRes.error.message)
                    }
                }
            } else {
                onError("Not connected to the network")
            }
        }
    }

    /**
     * Calls the api to update a password record
     */
    fun updatePassword(
        token: String,
        recordId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
//        if (recordId == null) {
//            onError("Record Not Found")
//            return
//        }
        viewModelScope.launch {
            _isMakingRequest.value = true
            val res = recordNet.updateRecord(
                updatedRecord = UpdateRecord(
                    password= _password.value,
                    email = if (_email.value == "") { null } else { _email.value.trim() },
                    username = if (_username.value == "") { null } else { _username.value.trim() },
                    service = _service.value.trim(),
                ),
                token = token,
                recordId = recordId
            )
            _isMakingRequest.value = false
            if (res != null) {
                when (res.code) {
                    HttpCodes.Ok.code, HttpCodes.Created.code, HttpCodes.NoContent.code -> {
                        onSuccess()
                    }
                    HttpCodes.BadRequest.code, HttpCodes.NotFound.code -> {
                        val error = Gson().fromJson(res.body?.string(), ErrorResponse::class.java)
                        onError(error.error.message)
                    }
                    else -> {
                        onError("Internal Service Error, Please Try Again Later.")
                    }
                }
            } else {
                onError("Not connected to the network")
            }
        }
    }
}