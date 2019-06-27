package com.akhbulatov.discusim.domain.thread

import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadInteractor @Inject constructor(
    private val threadRepository: ThreadRepository,
    private val forumRepository: ForumRepository
) {

    fun getTrendThreads(forumId: String?): Single<List<Thread>> =
        threadRepository.getTrendThreads(forumId)

    fun getHotThreads(forumId: String): Single<List<Thread>> =
        threadRepository.getHotThreads(forumId)

    fun getPopularThreads(forumId: String): Single<List<Thread>> =
        threadRepository.getPopularThreads(forumId)

    fun getThreads(forumId: String): Single<List<Thread>> = forumRepository.getThreads(forumId) // TODO
}