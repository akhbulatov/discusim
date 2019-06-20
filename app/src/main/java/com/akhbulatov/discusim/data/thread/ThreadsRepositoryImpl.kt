package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadsRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadsRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val threadResponseMapper: ThreadResponseMapper
) : ThreadsRepository {

    override fun getHotThreads(forumId: String): Single<List<Thread>> =
        api.getHotThreads(forumId, arrayListOf("forum", "author"))
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getPopularThreads(forumId: String): Single<List<Thread>> =
        api.getPopularThreads(forumId, arrayListOf("forum", "author"))
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}