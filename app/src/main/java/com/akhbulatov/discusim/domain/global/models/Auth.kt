package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

data class Auth(
    @Json(name = "user_id") val userId: Long,
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String
)