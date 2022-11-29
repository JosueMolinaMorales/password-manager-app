package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.Record
import okhttp3.Response

interface IRecordNetwork {
    suspend fun createSecret(record: Record): Response
}