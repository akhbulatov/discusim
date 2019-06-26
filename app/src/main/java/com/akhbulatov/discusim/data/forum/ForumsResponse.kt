package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.data.global.network.responses.BaseResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ForumsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val forums: List<ForumNetModel>
) : BaseResponse(cursor)