package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.global.network.models.CursorNetModel
import com.akhbulatov.discusim.data.global.network.responses.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CommentsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val comments: List<CommentNetModel>
) : CursorResponse(cursor)