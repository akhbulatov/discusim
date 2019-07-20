package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class UserDetailsNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "avatar") val avatar: AvatarNetModel,
    @Json(name = "url") val url: String,
    @Json(name = "isFollowing") val isFollowing: Boolean,
    @Json(name = "isPrivate") val isPrivate: Boolean,
    @Json(name = "about") val about: String,
    @Json(name = "location") val location: String,
    @Json(name = "numLikesReceived") val numLikesReceived: Int,
    @Json(name = "joinedAt") val joinedAt: LocalDateTime,
    @Json(name = "profileUrl") val profileUrl: String
)