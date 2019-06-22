package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class ForumNetModel(
    @Json(name = "pk") val pk: String,
    @Json(name = "name") val name: String
)