package com.example.password_manager_app.model

import com.example.password_manager_app.model.RecordType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class for a Record Object
 */
data class Record(
    @SerializedName(value = "record_type")
    val recordType: RecordType,
    @SerializedName(value = "_id")
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

/**
 * Data class for updating a record
 */
data class UpdateRecord(
    val key: String? = null,
    val secret: String? = null,
    val service: String? = null,
    val password: String? = null,
    val email: String? = null,
    val username: String? = null
)