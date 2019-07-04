package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class UserNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "avatar") val avatar: Avatar,
    @Json(name = "about") val about: String,
    @Json(name = "location") val location: String,
    @Json(name = "url") val url: String,
    @Json(name = "numLikesReceived") val numLikesReceived: Int,
    @Json(name = "joinedAt") val joinedAt: LocalDateTime

) {

    @JsonClass(generateAdapter = true)
    data class Avatar(@Json(name = "permalink") val permalink: String)
}