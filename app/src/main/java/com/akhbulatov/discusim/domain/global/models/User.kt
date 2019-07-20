package com.akhbulatov.discusim.domain.global.models

data class User(
    val id: Long,
    val name: String,
    val username: String,
    val avatarUrl: String
)