package com.akhbulatov.discusim.domain.global.models.forum

data class Forum(
    val id: String,
    val name: String,
    val faviconUrl: String,
    val channel: Channel?
) {

    companion object {
        fun isChannel(forumId: String): Boolean = forumId.startsWith("channel-")
    }
}
