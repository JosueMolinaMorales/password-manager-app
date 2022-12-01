package com.example.password_manager_app.network.app.record

import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IRecordNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class RecordNetwork: IRecordNetwork {
    private val client = OkHttpClient()

    override suspend fun createSecret(record: Record, token: String): Response {
        return withContext(Dispatchers.IO) {
            val requestBody = Gson().toJson(record).toRequestBody()
            val request = Request.Builder()
                .url("${Routes.PasswordManagerRoute.route}/record")
                .addHeader("Authorization", "Bearer $token")
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            response
        }
    }
}