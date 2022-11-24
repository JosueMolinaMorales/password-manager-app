package com.example.password_manager_app.network

enum class HttpCodes(val code: Int) {
    Ok(200),
    Created(201),
    NoContent(204),
    BadRequest(400),
    NotFound(404),
    ServerError(500)
}