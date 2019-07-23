package com.akhbulatov.discusim.data.topic

import com.akhbulatov.discusim.data.global.network.models.TopicNetModel
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Topic
import javax.inject.Inject

class TopicResponseMapper @Inject constructor() {

    fun map(response: TopicsResponse): PagedList<Topic> {
        val topics = map(response.topics)
        return PagedList(response.cursor?.next, topics)
    }

    fun map(models: List<TopicNetModel>): List<Topic> =
        models.map {
            Topic(
                it.identifier,
                it.displayName
            )
        }
}
