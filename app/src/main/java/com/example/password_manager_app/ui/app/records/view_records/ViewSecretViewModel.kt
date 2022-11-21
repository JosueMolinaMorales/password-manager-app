package com.example.password_manager_app.ui.app.records.view_records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ViewSecretViewModel: ViewModel() {
    private val _showPasswordView: MutableState<Boolean> = mutableStateOf(false)
    val show: State<Boolean> = _showPasswordView
    private val _service: MutableState<String> = mutableStateOf("")
    val title: State<String> = _service

    fun show(title: String) {
        //TODO refactor after record is sent, not string
        _service.value = title
        _showPasswordView.value = true
    }

    fun hide() {
        _showPasswordView.value = false
    }
}