package com.akhbulatov.discusim.domain.threads

import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadsInteractor @Inject constructor(
    private val forumsRepository: ForumsRepository
) {

    fun getThreads(forumId: String): Single<List<Thread>> = forumsRepository.getThreads(forumId) // TODO
}