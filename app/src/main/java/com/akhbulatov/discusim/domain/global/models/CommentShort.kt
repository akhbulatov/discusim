package com.akhbulatov.discusim.domain.global.models

import com.akhbulatov.discusim.domain.global.models.forum.ForumShort
import com.akhbulatov.discusim.domain.global.models.user.UserShort

data class CommentShort(
    val id: Long,
    val message: String,
    val author: UserShort,
    val discussion: DiscussionShort,
    val forum: ForumShort
)