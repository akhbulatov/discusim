package com.akhbulatov.discusim.data.global.network.models.vote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoteResponse(@Json(name = "response") val vote: VoteNetModel)