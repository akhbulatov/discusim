package com.akhbulatov.discusim.data.global.network.models

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.models.User
import com.squareup.moshi.Json

sealed class ActivityNetModel {
    data class MainNet(
        @Json(name = "object") val common: Common,
        @Json(name = "type") val type: String,
        @Json(name = "createdAt") val createdAt: String
    ) : ActivityNetModel() {

        data class Common(
            @Json(name = "id") val id: String,
            @Json(name = "vote") val vote: Int,
            @Json(name = "thread") val thread: Thread,
            @Json(name = "forum") val forum: Forum,
            @Json(name = "author") val author: User
        )
    }

    data class PostNet(
        @Json(name = "object") val post: Post,
        @Json(name = "type") val type: String,
        @Json(name = "createdAt") val createdAt: String
    ) : ActivityNetModel()
}