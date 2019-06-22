package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class ThreadNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String
)