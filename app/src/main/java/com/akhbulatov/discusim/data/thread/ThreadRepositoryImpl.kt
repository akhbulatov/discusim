package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import io.reactivex.Single
import javax.inject.Inject

class ThreadRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val threadResponseMapper: ThreadResponseMapper
) : ThreadRepository {

    override fun getTrendThreads(forumId: String?): Single<List<Thread>> =
        api.getTrendThreads(
            forumId,
            listOf(RequestParams.Related.AUTHOR)
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getHotThreads(forumId: String): Single<List<Thread>> =
        api.getHotThreads(
            forumId,
            listOf(
                RequestParams.Related.FORUM,
                RequestParams.Related.AUTHOR
            )
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getPopularThreads(forumId: String): Single<List<Thread>> =
        api.getPopularThreads(
            forumId,
            listOf(
                RequestParams.Related.FORUM,
                RequestParams.Related.AUTHOR
            )
        )
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}