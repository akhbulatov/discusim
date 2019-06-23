package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThreadNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String
)