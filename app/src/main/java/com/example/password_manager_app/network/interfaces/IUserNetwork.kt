package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.data.UpdateForm
import okhttp3.Response

interface IUserNetwork {
    suspend fun update(updateForm: UpdateForm): Response?
}