package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppLibraryNetModel(
    @Json(name = "name") val name: String,
    @Json(name = "author") val author: String,
    @Json(name = "license") val license: String,
    @Json(name = "web_url") val webUrl: String
)
