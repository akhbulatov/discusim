package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.Forum
import com.squareup.moshi.Json

data class UserForumsResponse(@Json(name = "response") val forums: List<Forum>)