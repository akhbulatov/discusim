package com.akhbulatov.discusim.data.global.network.models.forum

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForumNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "favicon") val favicon: FaviconNetModel,
    @Json(name = "channel") val channel: ChannelNetModel?
)
