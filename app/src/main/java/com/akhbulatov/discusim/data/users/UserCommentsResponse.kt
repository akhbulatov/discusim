package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.Comment
import com.squareup.moshi.Json

data class UserCommentsResponse(@Json(name = "response") val comments: List<Comment>)