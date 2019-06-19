package com.akhbulatov.discusim.domain.global.models

data class Session(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)