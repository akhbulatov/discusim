package com.akhbulatov.discusim.domain.global.models.forum

data class ForumDetails(
    val id: String,
    val name: String,
    val description: String?,
    val faviconUrl: String,
    val webUrl: String,
    val following: Boolean,
    val numDiscussions: Int,
    val numFollowers: Int,
    val channel: Channel?
)