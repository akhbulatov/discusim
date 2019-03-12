package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.domain.global.models.Forum
import com.squareup.moshi.Json

data class ForumResponse(@Json(name = "response") val forum: Forum)