package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.Record
import com.example.password_manager_app.model.UpdateRecord
import okhttp3.Response

interface IRecordNetwork {
    suspend fun createRecord(record: Record, token: String): Response
    suspend fun getRecord(recordId: String, token: String): Response
    suspend fun updateRecord(recordId: String, token: String, updatedRecord: UpdateRecord): Response
    suspend fun deleteRecord(token: String, recordId: String): Response
    suspend fun searchRecord(token: String, userId: String, query: String): Response
    suspend fun fetchRecords(token: String, userId: String): Response

}