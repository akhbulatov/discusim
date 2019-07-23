package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicNetModel(
    @Json(name = "identifier") val identifier: String,
    @Json(name = "displayName") val displayName: String
)
