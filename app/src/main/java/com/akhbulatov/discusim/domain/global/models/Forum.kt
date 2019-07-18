package com.akhbulatov.discusim.domain.global.models

data class Forum(
    val id: String,
    val name: String,
    val description: String?,
    val faviconUrl: String,
    val webUrl: String,
    val following: Boolean,
    val numDiscussions: Int,
    val numFollowers: Int,
    val channel: Channel?
) {

    data class Channel(
        val id: Long,
        val avatarUrl: String,
        val bannerUrl: String?,
        /** Используется в случае, когда [bannerUrl] равен `null`. **/
        val bannerColorHex: Int
    )
}