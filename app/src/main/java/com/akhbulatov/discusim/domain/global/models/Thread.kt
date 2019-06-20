package com.akhbulatov.discusim.domain.global.models

data class Thread(
    val id: String,
    val title: String,
    val message: String,
    val author: User,
    val createdAt: String,
    val mediaUrls: List<String>?,
    val likes: String,
    val posts: Int,
    val upvoted: Boolean,
    val forum: Forum
)