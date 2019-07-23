package com.akhbulatov.discusim.data.global.network.models.forum

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForumShortNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)
