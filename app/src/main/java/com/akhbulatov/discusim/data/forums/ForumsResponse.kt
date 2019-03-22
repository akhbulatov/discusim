package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.squareup.moshi.Json

data class ForumsResponse(@Json(name = "response") val forums: List<ForumNetModel>)