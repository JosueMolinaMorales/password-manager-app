package com.example.password_manager_app.network

class ErrorResponse(
    val error: ErrorMessage
) {}

class ErrorMessage(
    val message: String
) {}