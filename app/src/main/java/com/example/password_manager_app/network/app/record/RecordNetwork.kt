package com.example.password_manager_app.network.app.record

import android.util.Log
import com.example.password_manager_app.model.Record
import com.example.password_manager_app.model.UpdateRecord
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IRecordNetwork
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.lang.reflect.Type


class RecordNetwork: IRecordNetwork {
    private val client = OkHttpClient()


    override suspend fun createRecord(record: Record, token: String): Response {
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

    override suspend fun getRecord(recordId: String, token: String): Response {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("${Routes.PasswordManagerRoute.route}/record/$recordId/")
                .addHeader("Authorization", "Bearer $token")
                .get()
                .build()
            client.newCall(request).execute()
        }
    }

    override suspend fun updateRecord(recordId: String, token: String, updatedRecord: UpdateRecord): Response {
        return withContext(Dispatchers.IO) {
            val requestBody = Gson().toJson(updatedRecord).toRequestBody()
            val request = Request.Builder()
                .url("${Routes.PasswordManagerRoute.route}/record/$recordId")
                .addHeader("Authorization", "Bearer $token")
                .patch(requestBody)
                .build()
            val response = client.newCall(request).execute()
            response
        }
    }
    
    override suspend fun fetchRecords(token: String, userId: String): List<Record> {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("${Routes.PasswordManagerRoute.route}/record/${userId}/all")
                .addHeader("Authorization", "Bearer $token")
                .get()
                .build()
            val response = client.newCall(request).execute()
            val gson = Gson()
            val listType: Type = object: TypeToken<List<Record>>() {}.type
            if(response.body != null) {
                val list = gson.fromJson<List<Record>>(response.body!!.string(), listType)
                list
            } else {
                listOf()
            }
        }
    }
}
