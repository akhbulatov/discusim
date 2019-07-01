package com.akhbulatov.discusim.domain.global.models

data class Forum(
    val id: String,
    val name: String,
    val description: String?,
    val faviconUrl: String,
    val isFollowing: Boolean,
    val numFollowers: Int,
    val channel: Channel?
) {

    data class Channel(
        val id: Long,
        val avatarUrl: String,
        val bannerUrl: String
    )
}