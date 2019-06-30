package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.data.global.network.models.ThreadPreviewNetModel
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.models.ThreadPreview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: ThreadsResponse): List<Thread> = response.threads.map { map(it) }

    fun map(model: ThreadNetModel): Thread =
        model.let {
            Thread(
                it.id,
                it.title,
                if (it.message.isNotEmpty()) it.message else null,
                it.media?.map { media -> Thread.Media(media.url) },
                userResponseMapper.map(it.author),
                it.createdAt,
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