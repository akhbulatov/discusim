package com.akhbulatov.discusim.data.global.network.models

import com.akhbulatov.discusim.data.global.network.models.forum.ForumShortNetModel
import com.akhbulatov.discusim.data.global.network.models.user.UserShortNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class ActionNetModel(
    val obj: Any?,
    @Json(name = "type") val type: String,
    @Json(name = "createdAt") val createdAt: LocalDateTime
) {

    @JsonClass(generateAdapter = true)
    data class DiscussionVoteNetModel(
        @Json(name = "id") val id: String,
        @Json(name = "thread") val thread: DiscussionShortNetModel,
        @Json(name = "forum") val forum: ForumShortNetModel,
        @Json(name = "author") val author: UserShortNetModel
    )
}