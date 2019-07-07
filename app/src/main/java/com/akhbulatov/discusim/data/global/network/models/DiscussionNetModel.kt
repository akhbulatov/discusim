package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class DiscussionNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "media") val media: List<MediaNetModel>?,
    @Json(name = "author") val author: UserMiddleNetModel,
    @Json(name = "createdAt") val createdAt: LocalDateTime,
    @Json(name = "topics") val topics: List<TopicNetModel>?,
    @Json(name = "likes") val likes: Int,
    @Json(name = "userScore") val userScore: Int,
    @Json(name = "posts") val posts: Int
) {

    @JsonClass(generateAdapter = true)
    data class MediaNetModel(@Json(name = "url") val url: String)
}