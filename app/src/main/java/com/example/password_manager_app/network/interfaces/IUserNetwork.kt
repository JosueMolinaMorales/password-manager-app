package com.example.password_manager_app.network.interfaces

import com.example.password_manager_app.model.UpdateForm
import okhttp3.Response

interface IUserNetwork {
    /**
     * Updates a user's information
     *
     * @param updateForm The update information for the request
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    suspend fun update(updateForm: UpdateForm): Response?
}