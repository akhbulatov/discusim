package com.akhbulatov.discusim.domain.thread

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadInteractor @Inject constructor(
    private val threadRepository: ThreadRepository
) {

    fun getThreadDetails(threadId: Long): Single<Thread> =
        threadRepository.getThreadDetails(threadId)

    fun getThreads(forumId: String): Single<PagedList<Thread>> =
        threadRepository.getThreads(forumId)

    fun getHotThreads(forumId: String): Single<PagedList<Thread>> =
        threadRepository.getHotThreads(forumId)

    fun getPopularThreads(forumId: String): Single<PagedList<Thread>> =
        threadRepository.getPopularThreads(forumId)
}