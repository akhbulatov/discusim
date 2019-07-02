package com.akhbulatov.discusim.domain.thread

import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadInteractor @Inject constructor(
    private val threadRepository: ThreadRepository
) {

    fun getThreads(forumId: String): Single<List<Thread>> =
        threadRepository.getThreads(forumId)

    fun getHotThreads(forumId: String): Single<List<Thread>> =
        threadRepository.getHotThreads(forumId)

    fun getPopularThreads(forumId: String): Single<List<Thread>> =
        threadRepository.getPopularThreads(forumId)
}