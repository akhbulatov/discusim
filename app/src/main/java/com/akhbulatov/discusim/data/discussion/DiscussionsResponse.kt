package com.akhbulatov.discusim.data.discussion

import com.akhbulatov.discusim.data.global.network.models.DiscussionNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DiscussionsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val discussions: List<DiscussionNetModel>
) : CursorResponse(cursor)