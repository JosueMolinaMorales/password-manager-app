package com.example.password_manager_app.model

import com.example.password_manager_app.model.RecordType

data class Record(
    val recordType: RecordType,
    val id: String? = null,
    val user_id: String? = null,
    val key: String? = null,
    val secret: String? = null,
    val service: String? = null,
    val password: String? = null,
    val email: String? = null,
    val username: String? = null
) {
}