package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.forum.ChannelNetModel
import com.akhbulatov.discusim.data.global.network.models.forum.ForumDetailsNetModel
import com.akhbulatov.discusim.data.global.network.models.forum.ForumNetModel
import com.akhbulatov.discusim.data.global.network.models.forum.ForumShortNetModel
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.forum.Channel
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.domain.global.models.forum.ForumShort
import javax.inject.Inject

class ForumResponseMapper @Inject constructor() {

    fun map(response: ForumDetailsResponse): ForumDetails = map(response.forumDetails)

    fun map(response: ForumsResponse): PagedList<Forum> {
        val forums = response.forums.map { map(it) }
        return PagedList(response.cursor?.next, forums)
    }

    fun map(model: ForumDetailsNetModel): ForumDetails =
        model.let {
            ForumDetails(
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

    private fun mapChannel(model: ChannelNetModel): Channel =
        model.let {
            Channel(
                it.id.toLong(),
                it.avatar,
                it.banner ?: it.options.alertBackground?.let { bg -> "https:$bg" },
                it.bannerColorHex
            )
        }

    fun map(model: ForumNetModel): Forum =
        model.let {
            Forum(
                it.id,
                it.name,
                it.favicon.permalink,
                it.channel?.let { channel -> mapChannel(channel) }
            )
        }

    fun map(model: ForumShortNetModel): ForumShort =
        model.let {
            ForumShort(
                it.id,
                it.name
            )
        }
}