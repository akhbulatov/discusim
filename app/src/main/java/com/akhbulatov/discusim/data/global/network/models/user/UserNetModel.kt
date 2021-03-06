package com.akhbulatov.discusim.data.global.network.models.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "avatar") val avatar: AvatarNetModel
)
