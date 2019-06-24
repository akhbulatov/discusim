package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Thread
import io.reactivex.Single

interface ThreadRepository {
    fun getTrendThreads(forumId: String?): Single<List<Thread>>
    fun getHotThreads(forumId: String): Single<List<Thread>>
    fun getPopularThreads(forumId: String): Single<List<Thread>>
}