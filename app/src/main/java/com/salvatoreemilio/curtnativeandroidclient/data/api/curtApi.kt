package com.salvatoreemilio.curtnativeandroidclient.data.api

import com.salvatoreemilio.curtnativeandroidclient.model.Curt
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface CurtApi {
    @GET
    suspend fun getCurts(@Url host: String, @Header("api_key") key: String): List<Curt>?
}
