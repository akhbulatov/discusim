package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.domain.global.models.Post
import com.squareup.moshi.Json

data class UserPostsResponse(@Json(name = "response") val posts: List<Post>)