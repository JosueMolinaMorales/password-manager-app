package com.example.password_manager_app.network.User

import com.example.password_manager_app.data.UpdateForm
import com.example.password_manager_app.network.Routes
import com.example.password_manager_app.network.interfaces.IUserNetwork
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class UserNetwork: IUserNetwork {
    private val client = OkHttpClient()
    override suspend fun update(updateForm: UpdateForm): Response {
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
    }
}