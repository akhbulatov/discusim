package com.akhbulatov.discusim.data.global.network.models.comment

import com.akhbulatov.discusim.data.global.network.models.discussion.DiscussionShortNetModel
import com.akhbulatov.discusim.data.global.network.models.forum.ForumShortNetModel
import com.akhbulatov.discusim.data.global.network.models.user.UserShortNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentShortNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: UserShortNetModel,
    @Json(name = "parent") val parent: ParentNetModel?,
    @Json(name = "thread") val thread: DiscussionShortNetModel,
    @Json(name = "forum") val forum: ForumShortNetModel
) {

    @JsonClass(generateAdapter = true)
    data class ParentNetModel(@Json(name = "id") val id: String)
}
