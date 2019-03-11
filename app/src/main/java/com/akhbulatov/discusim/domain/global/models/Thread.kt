package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

data class Thread(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "likes") val likes: String,
    @Json(name = "posts") val posts: String,
    @Json(name = "author") val author: String,
    @Json(name = "forum") val forum: String
)