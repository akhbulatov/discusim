package com.akhbulatov.discusim.domain.global.models

import com.akhbulatov.discusim.domain.global.models.forum.ForumShort

data class CommentShort(
    val id: Long,
    val message: String,
    val author: UserShort,
    val discussion: DiscussionShort,
    val forum: ForumShort
)