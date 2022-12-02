package com.example.password_manager_app.ui.app.records.view_records

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.password_manager_app.model.Record

class ViewSecretViewModel: ViewModel() {
    private val _showSecretView: MutableState<Boolean> = mutableStateOf(false)
    val show: State<Boolean> = _showSecretView
    private val _record: MutableState<Record?> = mutableStateOf(null)
    val record: State<Record?> = _record

    fun show(record: Record) {
        _record.value = record
        _showSecretView.value = true
    }

    fun hide() {
        _showSecretView.value = false
    }
}