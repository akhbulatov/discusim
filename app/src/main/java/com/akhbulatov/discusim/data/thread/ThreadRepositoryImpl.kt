package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val threadResponseMapper: ThreadResponseMapper,
    private val schedulers: SchedulersProvider
) : ThreadRepository {

    override fun getThreads(forumId: String): Single<PagedList<Thread>> =
        api.getThreads(
            forumId,
            listOf(RequestParams.Thread.AUTHOR),
            listOf(RequestParams.Thread.TOPICS)
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getHotThreads(forumId: String): Single<PagedList<Thread>> =
        api.getHotThreads(
            forumId,
            listOf(RequestParams.Thread.AUTHOR)
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getPopularThreads(forumId: String): Single<PagedList<Thread>> =
        api.getPopularThreads(
            forumId,
            listOf(RequestParams.Thread.AUTHOR)
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}