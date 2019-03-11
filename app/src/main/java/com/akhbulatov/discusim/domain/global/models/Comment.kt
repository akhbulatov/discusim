package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

// TODO Переименовать класс на Post
data class Comment(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "author") val author: User,
    @Json(name = "likes") val likes: Int
)