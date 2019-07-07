package com.akhbulatov.discusim.data.global.network.responses

import com.akhbulatov.discusim.data.global.network.models.CursorNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class CursorResponse(@Json(name = "cursor") val cursor: CursorNetModel?)