package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.threads.ThreadsResponseMapper
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumsRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val forumsResponseMapper: ForumsResponseMapper,
    private val threadsResponseMapper: ThreadsResponseMapper
) : ForumsRepository {

    override fun getForumDetails(forumId: String): Single<Forum> =
        api.getForumDetails(forumId)
            .map { forumsResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getThreads(forumId: String): Single<List<Thread>> =
        api.getThreads(forumId, arrayListOf("forum", "author"))
            .map { threadsResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}