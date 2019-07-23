package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.akhbulatov.discusim.data.global.network.models.forum.ForumDetailsNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForumDetailsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val forumDetails: ForumDetailsNetModel
) : CursorResponse(cursor)
