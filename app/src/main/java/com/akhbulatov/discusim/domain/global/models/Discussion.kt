package com.akhbulatov.discusim.domain.global.models

import com.akhbulatov.discusim.domain.global.models.user.User
import org.threeten.bp.LocalDateTime

data class Discussion(
    val id: Long,
    val title: String,
    val message: String?,
    val mediaList: List<Media>,
    val author: User,
    val createdAt: LocalDateTime,
    val topics: List<Topic>,
    val vote: Vote,
    val comments: Int,
    val link: String
) {

    data class Media(val url: String)
}