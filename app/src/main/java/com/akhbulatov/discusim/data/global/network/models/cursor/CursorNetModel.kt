package com.akhbulatov.discusim.data.global.network.models.cursor

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CursorNetModel(@Json(name = "next") val next: String?)