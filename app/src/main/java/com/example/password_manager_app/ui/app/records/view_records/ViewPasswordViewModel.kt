package com.example.password_manager_app.ui.app.records.view_records

import android.icu.text.CaseMap.Title
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.password_manager_app.data.getRandomStreamingService
import com.example.password_manager_app.model.Record

class ViewPasswordViewModel: ViewModel() {
    private val _showPasswordView: MutableState<Boolean> = mutableStateOf(false)
    val show: State<Boolean> = _showPasswordView
    private val _record: MutableState<Record?> = mutableStateOf(null)
    val record: State<Record?> = _record

    private val _showPasswordValue: MutableState<Boolean> = mutableStateOf(false)
    val showPasswordValue: State<Boolean> = _showPasswordValue

    fun show(record: Record) {
        _record.value = record
        _showPasswordView.value = true
    }

    fun hide() {
        _showPasswordView.value = false
        _showPasswordValue.value = false
    }

    fun toggleShowPasswordValue(newValue: Boolean) {
        _showPasswordValue.value = !_showPasswordValue.value
    }
}