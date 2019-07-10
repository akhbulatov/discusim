package com.akhbulatov.discusim.data.follower

import com.akhbulatov.discusim.data.global.network.models.UserMiddleNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FollowersResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val followers: List<UserMiddleNetModel>
) : CursorResponse(cursor)