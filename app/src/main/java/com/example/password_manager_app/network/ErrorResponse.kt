package com.example.password_manager_app.network

/**
 * The Response Object received from the API
 */
class ErrorResponse(
    val error: ErrorMessage
) {}

/**
 * The Object that is contained within an error response
 */
class ErrorMessage(
    val message: String
) {}