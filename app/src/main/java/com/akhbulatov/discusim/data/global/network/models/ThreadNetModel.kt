package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class ThreadNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserNetModel,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "media") val media: List<Media>?,
    @Json(name = "likes") val likes: String,
    @Json(name = "posts") val posts: Int,
    @Json(name = "link") val link: String,
    @Json(name = "userScore") val userScore: Int,
    @Json(name = "forum") val forum: ForumNetModel
) {

    data class Media(@Json(name = "url") val url: String)
}