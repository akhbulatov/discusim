package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.domain.global.models.Comment
import com.squareup.moshi.Json

data class UserPostsResponse(@Json(name = "response") val posts: List<Comment>)