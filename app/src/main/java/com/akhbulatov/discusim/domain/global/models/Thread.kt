package com.akhbulatov.discusim.domain.global.models

data class Thread(
    val id: String,
    val title: String,
    val message: String,
    val author: User,
    val createdAt: String,
    val media: List<Media>?,
    val likes: String,
    val posts: Int,
    val closed: Boolean,
    val upvoted: Boolean,
    val forum: Forum
) {

    data class Media(val thumbnailUrl: String, val url: String)
}