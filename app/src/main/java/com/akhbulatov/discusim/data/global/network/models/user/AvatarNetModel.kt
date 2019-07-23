package com.akhbulatov.discusim.data.global.network.models.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarNetModel(@Json(name = "permalink") val permalink: String)
