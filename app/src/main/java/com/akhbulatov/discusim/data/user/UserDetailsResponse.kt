package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.models.user.UserDetailsNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserDetailsResponse(@Json(name = "response") val userDetails: UserDetailsNetModel)