package com.salvatoreemilio.curtnativeandroidclient.model

import com.google.gson.annotations.SerializedName

data class Curt(
    val url: String,
    val curt: String,
    val key: String,
    @SerializedName("TTL") val ttl: Short? = null,
    val expiresAt: Long? = null,
)