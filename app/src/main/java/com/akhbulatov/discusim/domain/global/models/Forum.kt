package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

data class Forum(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "favicon") val favicon: Favicon,
    @Json(name = "url") val url: String
) {

    data class Favicon(@Json(name = "permalink") val link: String)
}