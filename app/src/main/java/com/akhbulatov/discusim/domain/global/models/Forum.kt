package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

data class Forum(
    @Json(name = "pk") val pk: String, // TODO id?
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "favicon") val favicon: Favicon,
    @Json(name = "url") val url: String
) {

    data class Favicon(@Json(name = "permalink") val link: String)
}