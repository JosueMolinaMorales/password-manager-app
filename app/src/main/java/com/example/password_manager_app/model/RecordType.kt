package com.example.password_manager_app.model

/**
 * Enum class that denotes what the record type is for a record
 */
enum class RecordType(val value: String) {
    Password("Password"),
    Secret("Secret")
}