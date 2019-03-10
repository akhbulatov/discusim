package com.akhbulatov.discusim.domain.global.models

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: String,
    @Json(name = "username") val username: String,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: Avatar,
    @Json(name = "about") val about: String,
    @Json(name = "location") val location: String,
    @Json(name = "numLikesReceived") val upvotes: Int,
    @Json(name = "url") val url: String,
    @Json(name = "joinedAt") val joinedAt: String
) {

    data class Avatar(
        @Json(name = "small") val small: Small,
        @Json(name = "large") val large: Large
    ) {

        data class Small(@Json(name = "permalink") val link: String)
        data class Large(@Json(name = "permalink") val link: String)
    }
}