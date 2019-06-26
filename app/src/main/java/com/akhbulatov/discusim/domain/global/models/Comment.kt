package com.akhbulatov.discusim.domain.global.models

data class Comment(
    val id: Long,
    val message: String,
    val author: User,
    val thread: ThreadPreview
)