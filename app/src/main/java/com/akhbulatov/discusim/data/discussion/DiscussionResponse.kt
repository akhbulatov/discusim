package com.akhbulatov.discusim.data.discussion

import com.akhbulatov.discusim.data.global.network.models.discussion.DiscussionNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DiscussionResponse(@Json(name = "response") val discussion: DiscussionNetModel)
