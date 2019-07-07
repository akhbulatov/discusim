package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Discussion(
    val id: Long,
    val title: String,
    val message: String?,
    val mediaList: List<Media>,
    val author: UserMiddle,
    val createdAt: LocalDateTime,
    val topics: List<Topic>,
    val upvotes: Int,
    val isUpvoted: Boolean,
    val comments: Int
) {

    data class Media(val url: String)
}