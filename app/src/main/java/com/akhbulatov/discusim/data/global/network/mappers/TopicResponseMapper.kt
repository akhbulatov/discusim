package com.akhbulatov.discusim.data.global.network.mappers

import com.akhbulatov.discusim.data.global.network.models.TopicNetModel
import com.akhbulatov.discusim.domain.global.models.Topic
import javax.inject.Inject

class TopicResponseMapper @Inject constructor() {
    fun map(models: List<TopicNetModel>): List<Topic> =
        models.map {
            Topic(
                it.identifier,
                it.displayName
            )
        }
}