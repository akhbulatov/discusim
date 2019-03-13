package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.domain.global.models.User
import com.squareup.moshi.Json

data class ModeratorsResponse(@Json(name = "response") val moderators: List<Moderator>) {
    data class Moderator(@Json(name = "user") val user: User)
}