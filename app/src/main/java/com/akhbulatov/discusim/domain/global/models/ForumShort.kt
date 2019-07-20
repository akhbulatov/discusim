package com.akhbulatov.discusim.domain.global.models

data class ForumShort(
    val id: String,
    val name: String
) {

    fun isChannel(): Boolean = name.startsWith("channel-")
}