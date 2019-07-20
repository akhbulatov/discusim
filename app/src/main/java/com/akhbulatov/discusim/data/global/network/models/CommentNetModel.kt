package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class CommentNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserShortNetModel,
    @Json(name = "createdAt") val createdAt: LocalDateTime,
    @Json(name = "likes") val likes: Int,
    @Json(name = "userScore") val userScore: Int,
    @Json(name = "thread") val thread: DiscussionShortNetModel
)