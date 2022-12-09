package com.example.password_manager_app.network.app.record

import android.net.ConnectivityManager
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

/**
 * Network class for handling CRUD operations for Records
 */
class RecordNetwork(private val connectivityManager: ConnectivityManager): IRecordNetwork {
    private val client = OkHttpClient()


    /**
     * Creates a new record on the server.
     *
     * @param record The record to create.
     * @param token The authorization token to use for the request.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun createRecord(record: Record, token: String): Response? {
        if(connectivityManager.activeNetwork != null){
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
        } else {
            return null
        }
    }

    /**
     * Gets a record with the given ID from the server.
     *
     * @param recordId The ID of the record to get.
     * @param token The authorization token to use for the request.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun getRecord(recordId: String, token: String): Response? {
        if (connectivityManager.activeNetwork != null) {
            return withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("${Routes.PasswordManagerRoute.route}/record/$recordId/")
                    .addHeader("Authorization", "Bearer $token")
                    .get()
                    .build()
                client.newCall(request).execute()
            }
        }else{
            return null
        }
    }

    /**
     * Updates a record on the server with the given ID.
     *
     * @param recordId The ID of the record to update.
     * @param token The authorization token to use for the request.
     * @param updatedRecord The updated record.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun updateRecord(recordId: String, token: String, updatedRecord: UpdateRecord): Response? {
        if (connectivityManager.activeNetwork != null) {
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
        } else {
            return null
        }

    }

    /**
     * Fetches all records for a given user from the server.
     *
     * @param token The authorization token to use for the request.
     * @param userId The ID of the user whose records to fetch.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun fetchRecords(token: String, userId: String): Response? {
        if (connectivityManager.activeNetwork != null) {
            return withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("${Routes.PasswordManagerRoute.route}/record/${userId}/all")
                    .addHeader("Authorization", "Bearer $token")
                    .get()
                    .build()
                val response = client.newCall(request).execute()
                response
            }
        } else {
            return null
        }
    }

    /**
     * Deletes a record with the given ID from the server.
     *
     * @param token The authorization token to use for the request.
     * @param recordId The ID of the record to delete.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun deleteRecord(token: String, recordId: String): Response? {
        if (connectivityManager.activeNetwork != null) {
            return withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("${Routes.PasswordManagerRoute.route}/record/$recordId")
                    .delete()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                val response = client.newCall(request).execute()
                response
            }
        } else {
            return null
        }
    }

    /**
     * Searches for records on the server that match the given query.
     *
     * @param token The authorization token to use for the request.
     * @param userId The ID of the user whose records to search.
     * @param query The search query to use.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun searchRecord(token: String, userId: String, query: String): Response? {
        if(connectivityManager.activeNetwork != null) {
            return withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("${Routes.PasswordManagerRoute.route}/search/record/$userId?query=$query")
                    .get()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                val response = client.newCall(request).execute()
                response
            }
        } else {
            return null
        }

    }
}
