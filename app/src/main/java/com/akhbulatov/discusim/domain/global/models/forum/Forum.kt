package com.akhbulatov.discusim.domain.global.models.forum

data class Forum(
    val id: String,
    val name: String,
    val faviconUrl: String,
    val channel: Channel?
)