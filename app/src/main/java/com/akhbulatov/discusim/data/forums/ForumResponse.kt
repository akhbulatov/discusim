package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.squareup.moshi.Json

data class ForumResponse(@Json(name = "response") val forum: ForumNetModel)