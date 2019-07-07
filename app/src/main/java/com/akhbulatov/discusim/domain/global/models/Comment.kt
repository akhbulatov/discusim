package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Comment(
    val id: Long,
    val message: String,
    val author: UserPreview,
    val createdAt: LocalDateTime,
    val upvotes: Int,
    val voteType: VoteType,
    val discussion: DiscussionPreview
)