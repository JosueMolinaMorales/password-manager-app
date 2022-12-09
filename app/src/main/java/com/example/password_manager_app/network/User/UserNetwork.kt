package com.example.password_manager_app.network.User

import android.net.ConnectivityManager
import com.example.password_manager_app.model.UpdateForm
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IUserNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class UserNetwork(private val connectivityManager: ConnectivityManager): IUserNetwork {
    private val client = OkHttpClient()

    /**
     * Updates a user's information
     *
     * @param updateForm The update information for the request
     * @return A response object containing the server's response, or null if there is no active network connection.
     */
    override suspend fun update(updateForm: UpdateForm): Response? {
        if(connectivityManager.activeNetwork != null) {
            return withContext(Dispatchers.IO) {
                val requestBody = Gson().toJson(updateForm).toRequestBody()
                val request = Request
                    .Builder()
                    .addHeader("Authorization", "Bearer ${updateForm.token}")
                    .patch(requestBody)
                    .url("${Routes.PasswordManagerRoute.route}/user/${updateForm.user_id}")
                    .build()
                client.newCall(request).execute()
            }
        } else {
            return null
        }
    }
}