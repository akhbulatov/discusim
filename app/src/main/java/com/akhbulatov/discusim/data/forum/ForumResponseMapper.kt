package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.data.global.network.models.ForumPreviewNetModel
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.ForumPreview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForumResponseMapper @Inject constructor() {
    fun map(response: ForumsResponse): List<Forum> = response.forums.map { map(it) }

    fun map(response: ForumResponse): Forum = map(response.forum)

    fun map(model: ForumNetModel): Forum =
        model.let {
            Forum(
                it.id,
                it.name,
                it.description,
                it.favicon.permalink,
                it.channel?.let { channel -> mapChannel(channel) }
            )
        }

    private fun mapChannel(model: ForumNetModel.ChannelNetModel): Forum.Channel =
        model.let {
            Forum.Channel(
                it.id.toLong(),
                it.avatar,
                "https:${it.options.alertBackground}"
            )
        }

    fun map(model: ForumPreviewNetModel): ForumPreview =
        model.let {
            ForumPreview(
                it.id,
                it.name
            )
        }
}