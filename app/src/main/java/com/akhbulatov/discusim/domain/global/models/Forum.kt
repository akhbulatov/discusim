package com.akhbulatov.discusim.domain.global.models

data class Forum(
    val id: String,
    val name: String,
    val description: String?,
    val faviconUrl: String,
    val url: String,
    val numThreads: Int?,
    val numFollowers: Int?,
    val numModerators: Int?,
    val channel: Channel?
) {

    data class Channel(
        val bannerColor: String,
        val backgroundUrl: String,
        val logoUrl: String,
        val guidelinesUrl: String
    )
}