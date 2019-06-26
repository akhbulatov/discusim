package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class ActionNetModel(
    val obj: Any?,
    @Json(name = "type") val type: String,
    @Json(name = "createdAt") val createdAt: LocalDateTime
) {

    @JsonClass(generateAdapter = true)
    data class ThreadVoteNetModel(
        @Json(name = "id") val id: String,
        @Json(name = "thread") val thread: ThreadPreviewNetModel,
        @Json(name = "forum") val forum: ForumPreviewNetModel,
        @Json(name = "author") val author: UserNetModel
    )
}