package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.data.global.network.models.ThreadPreviewNetModel
import com.akhbulatov.discusim.data.topic.TopicResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.models.ThreadPreview
import javax.inject.Inject

class ThreadResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val topicResponseMapper: TopicResponseMapper
) {

    fun map(response: ThreadsResponse): PagedList<Thread> {
        val threads = response.threads.map { map(it) }
        return PagedList(response.cursor?.next, threads)
    }

    fun map(model: ThreadNetModel): Thread =
        model.let {
            Thread(
                it.id,
                it.title,
                if (it.message.isNotEmpty()) it.message else null,
                it.media?.let { list -> list.map { media -> Thread.Media(media.url) } } ?: emptyList(),
                userResponseMapper.map(it.author),
                it.createdAt,
                it.topics?.let { topic -> topicResponseMapper.map(topic) } ?: emptyList(),
                it.likes,
                it.userScore > 0,
                it.posts
            )
        }

    fun map(model: ThreadPreviewNetModel): ThreadPreview =
        model.let {
            ThreadPreview(
                it.id,
                it.title
            )
        }
}