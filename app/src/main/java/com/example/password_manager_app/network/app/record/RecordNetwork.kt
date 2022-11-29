package com.example.password_manager_app.network.app.record

import com.example.password_manager_app.model.Record
import com.example.password_manager_app.network.interfaces.IRecordNetwork
import okhttp3.Response

class RecordNetwork: IRecordNetwork {
    override suspend fun createSecret(record: Record): Response {
        TODO("Not yet implemented")
    }
}