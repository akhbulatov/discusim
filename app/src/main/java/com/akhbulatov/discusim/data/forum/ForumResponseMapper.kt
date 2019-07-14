package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.data.global.network.models.ForumPreviewNetModel
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.ForumPreview
import com.akhbulatov.discusim.domain.global.models.PagedList
import javax.inject.Inject

class ForumResponseMapper @Inject constructor() {

    fun map(response: ForumsResponse): PagedList<Forum> {
        val forums = response.forums.map { map(it) }
        return PagedList(response.cursor?.next, forums)
    }

    fun map(response: ForumResponse): Forum = map(response.forum)

    fun map(model: ForumNetModel): Forum =
        model.let {
            Forum(
                it.id,
                it.name,
                it.description,
                it.favicon.permalink,
                it.url,
                it.isFollowing,
                it.numThreads,
                it.numFollowers,
                it.channel?.let { channel -> mapChannel(channel) }
            )
        }

    private fun mapChannel(model: ForumNetModel.ChannelNetModel): Forum.Channel =
        model.let {
            Forum.Channel(
                it.id.toLong(),
                it.avatar,
                it.banner ?: it.options.alertBackground?.let { bg -> "https:$bg" },
                it.bannerColorHex
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