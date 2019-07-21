package com.akhbulatov.discusim.domain.global.models.forum

data class Channel(
    val id: Long,
    val avatarUrl: String,
    val bannerUrl: String?,
    /** Используется в случае, когда [bannerUrl] равен `null`. **/
    val bannerColorHex: Int
)