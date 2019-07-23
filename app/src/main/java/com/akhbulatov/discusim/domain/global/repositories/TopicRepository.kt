package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Topic
import io.reactivex.Single

interface TopicRepository {
    fun geTrendingTopics(forumId: String): Single<PagedList<Topic>>
}
