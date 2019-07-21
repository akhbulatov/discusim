package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.akhbulatov.discusim.data.global.network.models.forum.ForumNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForumsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val forums: List<ForumNetModel>
) : CursorResponse(cursor)