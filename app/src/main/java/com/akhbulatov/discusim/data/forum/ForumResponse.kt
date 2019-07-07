package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForumResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val forum: ForumNetModel
) : CursorResponse(cursor)