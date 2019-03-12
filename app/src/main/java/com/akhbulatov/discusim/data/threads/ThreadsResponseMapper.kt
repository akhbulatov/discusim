package com.akhbulatov.discusim.data.threads

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.domain.global.models.Thread
import javax.inject.Inject

class ThreadsResponseMapper @Inject constructor() {
    fun map(response: ThreadsResponse): List<Thread> = response.threads.map { map(it) }

    fun map(model: ThreadNetModel): Thread =
        model.let {
            Thread(
                it.id,
                it.title,
                it.message,
                it.author,
                it.createdAt,
                it.media?.map { media -> Thread.Media(media.thumbnailUrl, media.url) },
                it.likes,
                it.posts,
                it.closed,
                it.userScore > 0,
                it.forum
            )
        }
}