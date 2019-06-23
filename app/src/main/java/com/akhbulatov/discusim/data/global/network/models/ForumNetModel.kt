package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForumNetModel(
    @Json(name = "pk") val pk: String,
    @Json(name = "name") val name: String
)