package com.akhbulatov.discusim.data.topic

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Topic
import com.akhbulatov.discusim.domain.global.repositories.TopicRepository
import io.reactivex.Single
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val topicResponseMapper: TopicResponseMapper,
    private val schedulers: SchedulersProvider
) : TopicRepository {

    override fun geTrendingTopics(forumId: String): Single<PagedList<Topic>> =
        api.getTrendingTopics(forumId.substringAfter("channel-")) // TODO
            .map { topicResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}
