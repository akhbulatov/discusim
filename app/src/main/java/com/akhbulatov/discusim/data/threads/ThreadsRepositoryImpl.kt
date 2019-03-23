package com.akhbulatov.discusim.data.threads

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadsRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadsRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val threadsResponseMapper: ThreadsResponseMapper
) : ThreadsRepository {

    override fun getHotThreads(forumId: String): Single<List<Thread>> =
        api.getHotThreads(forumId, arrayListOf("forum", "author"))
            .map { threadsResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getPopularThreads(forumId: String): Single<List<Thread>> =
        api.getPopularThreads(forumId, arrayListOf("forum", "author"))
            .map { threadsResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}