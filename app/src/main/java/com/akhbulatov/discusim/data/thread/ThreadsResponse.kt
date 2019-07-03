package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.data.global.network.responses.BaseResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ThreadsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val threads: List<ThreadNetModel>
) : BaseResponse(cursor)