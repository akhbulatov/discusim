package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserShortNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: AvatarNetModel
)