package com.akhbulatov.discusim.data.following

import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.akhbulatov.discusim.data.global.network.models.user.UserNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FollowingResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val following: List<UserNetModel>
) : CursorResponse(cursor)