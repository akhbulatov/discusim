package com.akhbulatov.discusim.domain.global.models

data class Comment(
    val id: String,
    val message: String,
    val author: User
)