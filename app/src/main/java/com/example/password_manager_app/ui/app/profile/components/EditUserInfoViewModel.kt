package com.example.password_manager_app.ui.app.profile.components

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.password_manager_app.data.PasswordManagerDatabase
import com.example.password_manager_app.model.UpdateForm
import com.example.password_manager_app.model.User
import com.example.password_manager_app.network.ErrorResponse
import com.example.password_manager_app.network.HttpCodes
import com.example.password_manager_app.network.User.UserNetwork
import com.google.gson.Gson
import kotlinx.coroutines.launch

/**
 * The EditUserInfoViewModel that handles the logic for updating a users information
 */
class EditUserInfoViewModel(app: Application): AndroidViewModel(app) {
    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> = _password
    private val _passwordHasError: MutableState<Boolean> = mutableStateOf(false)

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> = _username

    private val _newPassword: MutableState<String> = mutableStateOf("")
    val newPassword: State <String> = _newPassword
    private val _newPasswordHasError: MutableState<Boolean> = mutableStateOf(false)

    private val _confirmPassword: MutableState<String> = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword
    private val _confirmPasswordHasError: MutableState<Boolean> = mutableStateOf(false)

    private val _updateErrorMsg: MutableState<String?> = mutableStateOf(null)
    val updateErrorMsg: State<String?> = _updateErrorMsg

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email
    private val _emailHasError: MutableState<Boolean> = mutableStateOf(false)

    private val _makingRequest: MutableState<Boolean> = mutableStateOf(false)

    private val ctx = getApplication<Application>()
    private val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val userNetwork: UserNetwork = UserNetwork(connectivityManager)
    private val userDb: PasswordManagerDatabase

    init {
        userDb = Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            "passwordManager.db"
        ).build()
    }

    /**
     * Sets the password
     */
    fun setPassword(password: String){
        _password.value = password
    }

    /**
     * Sets the new password
     */
    fun setNewPassword(newPassword: String) {
        _newPassword.value = newPassword
        _confirmPasswordHasError.value = _confirmPassword.value != _newPassword.value
        _newPasswordHasError.value = _confirmPassword.value != _newPassword.value
    }

    /**
     * Sets the confirm password field
     */
    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _confirmPasswordHasError.value = _confirmPassword.value != _newPassword.value
        _newPasswordHasError.value = _confirmPassword.value != _newPassword.value
    }

    /**
     * Sets the email field
     */
    fun setEmail(email: String) {
        _email.value = email
        _emailHasError.value = _email.value == ""
    }

    /**
     * Validates the email
     */
    fun validateEmail(): String? {
        var errorMsg = ""
        val emptyFields = mutableListOf<String>()
        if(_password.value == "") {
            _passwordHasError.value = true
            emptyFields.add("password")
        }
        if(_email.value == ""){
            emptyFields.add("email")
        }
        if(emptyFields.size != 0) {
            emptyFields.forEachIndexed { index, value ->
                errorMsg += if (index != (emptyFields.size-1)) {"$value, " } else { value }
            }
            return if (emptyFields.size == 1){
                "$errorMsg is required"
            } else {
                "$errorMsg are required"
            }
        }
        return null
    }

    /**
     * Validates the Password
     */
    fun validatePassword(): String? {
        var errorMsg = ""
        val emptyFields = mutableListOf<String>()
        if(_password.value == "") {
            _passwordHasError.value = true
            emptyFields.add("password")
        }
        if(_newPassword.value == ""){
            emptyFields.add("new password")
        }
        if(_confirmPassword.value == ""){
            emptyFields.add("confirm new password")
        }

        if(emptyFields.size != 0) {
            emptyFields.forEachIndexed { index, value ->
                errorMsg += if (index != (emptyFields.size-1)) {"$value, " } else { value }
            }
            return if (emptyFields.size == 1){
                "$errorMsg is required"
            } else {
                "$errorMsg are required"
            }
        }

        if(_newPassword.value != _confirmPassword.value) {
            _confirmPasswordHasError.value = _confirmPassword.value != _newPassword.value
            _newPasswordHasError.value = _confirmPassword.value != _newPassword.value
            return "Passwords do not match"
        }

        return null
    }

    /**
     * Calls the API to update the users information
     */
    fun updateInfo(
        onSuccessfulUpdate: (User) -> Unit,
        onUnsuccessfulUpdate: () -> Unit,
    ) {
        viewModelScope.launch {
            val user: User? = userDb.userDao().getUser()
            _makingRequest.value = true
            val response = userNetwork.update(
                UpdateForm(
                email = if(_email.value == "") {null} else {_email.value.trim()},
                password = _password.value.trim(),
                new_password = if(_newPassword.value == "") { null } else { _newPassword.value.trim() },
                token = user?.token ?: "",
                user_id = user?.id ?: ""
            )
            )
            _makingRequest.value = false
            if(response != null) {
                val body = response.body?.string()
                when (response.code) {
                    HttpCodes.NoContent.code -> {
                        val newUser = User(
                            email = if (_email.value != "") { _email.value } else { user?.email!! },
                            token = user?.token ?: "",
                            id = user?.id ?: "",
                            name = user?.name ?: "",
                            username = user?.username ?: ""
                        )
                        if (user != null) {
                            userDb.userDao().updateUser(newUser)
                        }
                        _email.value = ""
                        _password.value = ""
                        _newPassword.value = ""
                        _confirmPassword.value = ""
                        onSuccessfulUpdate(newUser)
                    }
                    HttpCodes.BadRequest.code, HttpCodes.NotFound.code -> {
                        val errorBody = Gson().fromJson(body, ErrorResponse::class.java)
                        _updateErrorMsg.value = errorBody.error.message
                        onUnsuccessfulUpdate()
                    }
                    else -> {
                        _updateErrorMsg.value = "Internal Service Error, Please try again later"
                        onUnsuccessfulUpdate()
                    }
                }
            } else {
                _updateErrorMsg.value = "Not Connected To Wifi"
                onUnsuccessfulUpdate()
            }
        }
    }
}