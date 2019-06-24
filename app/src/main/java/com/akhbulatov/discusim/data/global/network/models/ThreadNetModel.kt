package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class ThreadNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserNetModel,
    @Json(name = "createdAt") val createdAt: LocalDateTime,
    @Json(name = "likes") val likes: Int,
    @Json(name = "userScore") val userScore: Int,
    @Json(name = "posts") val posts: Int
)