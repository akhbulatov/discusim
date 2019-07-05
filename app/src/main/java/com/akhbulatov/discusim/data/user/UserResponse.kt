package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserResponse(@Json(name = "response") val user: UserNetModel)