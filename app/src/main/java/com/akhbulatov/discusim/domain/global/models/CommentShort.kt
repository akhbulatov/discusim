package com.akhbulatov.discusim.domain.global.models

data class CommentShort(
    val id: Long,
    val message: String,
    val author: UserShort,
    val discussion: DiscussionShort,
    val forum: ForumShort
)