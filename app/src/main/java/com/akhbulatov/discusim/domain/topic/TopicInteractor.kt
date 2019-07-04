package com.akhbulatov.discusim.domain.topic

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Topic
import com.akhbulatov.discusim.domain.global.repositories.TopicRepository
import io.reactivex.Single
import javax.inject.Inject

class TopicInteractor @Inject constructor(
    private val topicRepository: TopicRepository
) {

    fun getTrendingTopics(forumId: String): Single<PagedList<Topic>> =
        topicRepository.geTrendingTopics(forumId)
}