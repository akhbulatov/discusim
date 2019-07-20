package com.akhbulatov.discusim.domain.global.models

data class CommentPreview(
    val id: Long,
    val message: String,
    val author: UserShort,
    val discussion: DiscussionPreview
)