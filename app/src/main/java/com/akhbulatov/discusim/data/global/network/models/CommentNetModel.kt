package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserNetModel
)