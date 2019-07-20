package com.akhbulatov.discusim.data.moderator

import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ModeratorsResponse(
    cursor: CursorNetModel?,
    @Json(name = "response") val moderators: List<ModeratorNetModel>
) : CursorResponse(cursor) {

    data class ModeratorNetModel(@Json(name = "user") val user: UserNetModel)
}