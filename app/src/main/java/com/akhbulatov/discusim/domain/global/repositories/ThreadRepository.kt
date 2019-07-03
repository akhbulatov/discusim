package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Thread
import io.reactivex.Single

interface ThreadRepository {
    fun getThreads(forumId: String): Single<PagedList<Thread>>
    fun getHotThreads(forumId: String): Single<PagedList<Thread>>
    fun getPopularThreads(forumId: String): Single<PagedList<Thread>>
}