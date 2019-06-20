package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

sealed class ActionNetModel {
    data class ThreadVoteContainer(
        @Json(name = "object") val threadVote: ThreadVoteNet,
        @Json(name = "type") val type: String,
        @Json(name = "createdAt") val createdAt: String
    ) : ActionNetModel() {

        data class ThreadVoteNet(
            @Json(name = "id") val id: String,
            @Json(name = "vote") val vote: Int,
            @Json(name = "thread") val thread: ThreadNet,
            @Json(name = "forum") val forum: ForumNetModel,
            @Json(name = "author") val author: UserNetModel
        ) {

            data class ThreadNet(
                @Json(name = "id") val id: String,
                @Json(name = "title") val title: String
            )
        }
    }

    data class PostNet(
        @Json(name = "object") val post: PostNetModel,
        @Json(name = "type") val type: String,
        @Json(name = "createdAt") val createdAt: String
    ) : ActionNetModel()
}