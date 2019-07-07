package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentPreviewNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserPreviewNetModel,
    @Json(name = "thread") val thread: DiscussionPreviewNetModel
)