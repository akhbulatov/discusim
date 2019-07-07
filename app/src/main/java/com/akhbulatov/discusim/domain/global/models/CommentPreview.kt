package com.akhbulatov.discusim.domain.global.models

data class CommentPreview(
    val id: Long,
    val message: String,
    val author: UserPreview,
    val discussion: DiscussionPreview
)