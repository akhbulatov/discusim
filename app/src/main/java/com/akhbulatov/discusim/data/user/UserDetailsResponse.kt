package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.squareup.moshi.Json

data class UserDetailsResponse(@Json(name = "response") val userDetails: UserDetails)