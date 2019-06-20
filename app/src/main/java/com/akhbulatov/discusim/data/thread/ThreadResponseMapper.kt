package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.forums.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Thread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadResponseMapper @Inject constructor(
    private val userResponseMappeer: UserResponseMapper,
    private val forumResponseMapper: ForumResponseMapper
) {

    fun map(response: ThreadsResponse): List<Thread> = response.threads.map { map(it) }

    fun map(model: ThreadNetModel): Thread =
        model.let {
            Thread(
                it.id,
                it.title,
                it.message,
                userResponseMappeer.map(it.author),
                it.createdAt,
                it.media?.map { media -> media.url },
                it.likes,
                it.posts,
                it.userScore > 0,
                forumResponseMapper.map(it.forum)
            )
        }
}