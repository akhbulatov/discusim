package com.akhbulatov.discusim.data.global.network.models.forum

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForumDetailsNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "favicon") val favicon: FaviconNetModel,
    @Json(name = "url") val url: String,
    @Json(name = "isFollowing") val isFollowing: Boolean,
    @Json(name = "numThreads") val numThreads: Int,
    @Json(name = "numFollowers") val numFollowers: Int,
    @Json(name = "channel") val channel: ChannelNetModel?
)
