package com.akhbulatov.discusim.domain.global.models

data class Auth(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)