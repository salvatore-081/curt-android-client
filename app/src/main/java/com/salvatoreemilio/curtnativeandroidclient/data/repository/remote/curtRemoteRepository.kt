package com.salvatoreemilio.curtnativeandroidclient.data.repository.remote

import com.salvatoreemilio.curtnativeandroidclient.data.api.CurtApi
import com.salvatoreemilio.curtnativeandroidclient.model.Curt
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurtRepository() {
    private val api: CurtApi by lazy {
        createCurtApi()
    }

    suspend fun getCurts(host: String, key: String): List<Curt>? = withContext(Dispatchers.IO) {
        return@withContext api.getCurts("$host/c", key)
    }

    private fun createCurtApi(): CurtApi {
        val retrofit = Retrofit.Builder().baseUrl("https://placeholder.com").addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).build()
        return retrofit.create(CurtApi::class.java)
    }
}