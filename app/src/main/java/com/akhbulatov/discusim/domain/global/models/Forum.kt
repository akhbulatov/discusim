package com.akhbulatov.discusim.domain.global.models

data class Forum(
    val id: String,
    val name: String,
    val description: String?,
    val faviconUrl: String,
    val url: String,
    val channel: Channel?
) {

    data class Channel(
        val id: String,
        val name: String,
        val bannerColor: String,
        val description: String?,
        val backgroundUrl: String,
        val logoUrl: String,
        val guidelinesUrl: String
    )
}