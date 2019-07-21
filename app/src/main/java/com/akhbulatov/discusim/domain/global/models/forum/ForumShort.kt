package com.akhbulatov.discusim.domain.global.models.forum

data class ForumShort(
    val id: String,
    val name: String
) {

    fun isChannel(): Boolean = name.startsWith("channel-")
}