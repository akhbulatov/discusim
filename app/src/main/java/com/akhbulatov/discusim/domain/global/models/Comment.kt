package com.akhbulatov.discusim.domain.global.models

import com.akhbulatov.discusim.domain.global.models.user.UserShort
import org.threeten.bp.LocalDateTime

data class Comment(
    val id: Long,
    val message: String,
    val author: UserShort,
    val createdAt: LocalDateTime,
    val vote: Vote,
    val discussion: DiscussionShort
)