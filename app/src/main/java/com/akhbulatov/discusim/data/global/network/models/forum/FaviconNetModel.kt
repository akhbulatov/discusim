package com.akhbulatov.discusim.data.global.network.models.forum

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FaviconNetModel(@Json(name = "permalink") val permalink: String)
