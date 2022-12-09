package com.example.password_manager_app.model

/**
 * Enum Class that denotes what action will be taking for the createRecord Route
 */
enum class ActionOnRecord(val value: String) {
    Create(value = "create"),
    Update(value = "update")
}