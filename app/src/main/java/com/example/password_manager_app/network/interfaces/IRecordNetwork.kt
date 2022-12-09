package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.Record
import com.example.password_manager_app.model.UpdateRecord
import okhttp3.Response

interface IRecordNetwork {
    /**
     * Creates a new record on the server.
     *
     * @param record The record to create.
     * @param token The authorization token to use for the request.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun createRecord(record: Record, token: String): Response?

    /**
     * Gets a record with the given ID from the server.
     *
     * @param recordId The ID of the record to get.
     * @param token The authorization token to use for the request.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun getRecord(recordId: String, token: String): Response?

    /**
     * Updates a record on the server with the given ID.
     *
     * @param recordId The ID of the record to update.
     * @param token The authorization token to use for the request.
     * @param updatedRecord The updated record.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun updateRecord(recordId: String, token: String, updatedRecord: UpdateRecord): Response?

    /**
     * Deletes a record with the given ID from the server.
     *
     * @param token The authorization token to use for the request.
     * @param recordId The ID of the record to delete.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun deleteRecord(token: String, recordId: String): Response?

    /**
     * Fetches all records for a given user from the server.
     *
     * @param token The authorization token to use for the request.
     * @param userId The ID of the user whose records to fetch.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun fetchRecords(token: String, userId: String): Response?

    /**
     * Searches for records on the server that match the given query.
     *
     * @param token The authorization token to use for the request.
     * @param userId The ID of the user whose records to search.
     * @param query The search query to use.
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun searchRecord(token: String, userId: String, query: String): Response?

}