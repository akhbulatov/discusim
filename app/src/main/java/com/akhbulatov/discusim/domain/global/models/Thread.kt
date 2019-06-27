package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Thread(
    val id: String,
    val title: String,
    val message: String?,
    val mediaList: List<Media>?,
    val author: User,
    val createdAt: LocalDateTime,
    val upvotes: Int,
    val upvoted: Boolean,
    val comments: Int
) {

    data class Media(val url: String)
}