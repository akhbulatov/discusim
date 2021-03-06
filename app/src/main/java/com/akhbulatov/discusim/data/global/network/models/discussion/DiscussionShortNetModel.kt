package com.akhbulatov.discusim.data.global.network.models.discussion

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscussionShortNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "likes") val likes: Int
)
