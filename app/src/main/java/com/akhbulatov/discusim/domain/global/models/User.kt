package com.akhbulatov.discusim.domain.global.models

data class User(
    val id: String,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val about: String,
    val location: String,
    val url: String,
    val joinedAt: String
)