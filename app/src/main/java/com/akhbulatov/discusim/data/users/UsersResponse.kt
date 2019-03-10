package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.User
import com.squareup.moshi.Json

data class UsersResponse(@Json(name = "response") val users: List<User>)