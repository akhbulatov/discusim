package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class SessionNetModel(
    @Json(name = "user_id") val userId: Long,
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String
)