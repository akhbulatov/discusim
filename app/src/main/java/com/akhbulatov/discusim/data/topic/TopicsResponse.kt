package com.akhbulatov.discusim.data.topic

import com.akhbulatov.discusim.data.global.network.models.TopicNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TopicsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val topics: List<TopicNetModel>
) : CursorResponse(cursor)
