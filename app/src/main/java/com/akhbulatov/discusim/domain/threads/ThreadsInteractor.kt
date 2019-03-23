package com.akhbulatov.discusim.domain.threads

import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import com.akhbulatov.discusim.domain.global.repositories.ThreadsRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadsInteractor @Inject constructor(
    private val forumsRepository: ForumsRepository,
    private val threadsRepository: ThreadsRepository
) {

    fun getThreads(forumId: String): Single<List<Thread>> = forumsRepository.getThreads(forumId) // TODO

    fun getHotThreads(forumId: String): Single<List<Thread>> = threadsRepository.getHotThreads(forumId)

    fun getPopularThreads(forumId: String): Single<List<Thread>> = threadsRepository.getPopularThreads(forumId)
}