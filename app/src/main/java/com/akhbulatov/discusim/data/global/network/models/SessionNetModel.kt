package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionNetModel(
    @Json(name = "user_id") val userId: Long,
    @Json(name = "access_token") val accessToken: String
)