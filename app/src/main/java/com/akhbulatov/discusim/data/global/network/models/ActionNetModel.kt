package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class ActionNetModel(
    val `object`: Any?,
    @Json(name = "type") val type: String,
    @Json(name = "createdAt") val createdAt: String
) {

    data class ThreadVoteNetModel(
        @Json(name = "id") val id: String,
        @Json(name = "thread") val thread: ThreadNetModel,
        @Json(name = "forum") val forum: ForumNetModel,
        @Json(name = "author") val author: UserNetModel,
        @Json(name = "vote") val vote: Int
    )
}