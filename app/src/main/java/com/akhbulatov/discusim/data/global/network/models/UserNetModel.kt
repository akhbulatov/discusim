package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class UserNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "username") val username: String,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: Avatar,
    @Json(name = "about") val about: String,
    @Json(name = "location") val location: String,
    @Json(name = "url") val url: String,
    @Json(name = "joinedAt") val joinedAt: String
) {

    data class Avatar(@Json(name = "large") val large: Large) {
        data class Large(@Json(name = "permalink") val link: String)
    }
}