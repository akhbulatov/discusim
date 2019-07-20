package com.akhbulatov.discusim.data.global.network.models.vote

import com.akhbulatov.discusim.data.global.network.models.DiscussionShortNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VoteNetModel(
    @Json(name = "vote") val vote: Int,
    @Json(name = "thread") val thread: DiscussionShortNetModel
)