package com.akhbulatov.discusim.data.global.network.models

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.squareup.moshi.Json

data class ThreadNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: User,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "media") val media: List<Media>?,
    @Json(name = "likes") val likes: String,
    @Json(name = "posts") val posts: Int,
    @Json(name = "isClosed") val closed: Boolean,
    @Json(name = "link") val link: String,
    @Json(name = "userScore") val userScore: Int,
    @Json(name = "forum") val forum: Forum
) {

    data class Media(
        @Json(name = "thumbnailUrl") val thumbnailUrl: String,
        @Json(name = "url") val url: String
    )
}