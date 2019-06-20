package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.domain.global.models.Forum
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
                it.channel?.avatarUrl ?: it.favicon.link,
                it.url,
                it.numThreads,
                it.numFollowers,
                it.numModerators,
                it.channel?.let { channel ->
                    Forum.Channel(
                        "http:${channel.options.backgroundUrl}", // TODO Добавить коммент про CDN
                        "http:${channel.options.logo.url}", // TODO Добавить коммент про CDN
                        channel.options.guidelinesUrl
                    )
                }
            )
        }
}