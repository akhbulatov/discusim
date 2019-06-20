package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class PostNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "author") val author: UserNetModel,
    @Json(name = "likes") val likes: Int
)