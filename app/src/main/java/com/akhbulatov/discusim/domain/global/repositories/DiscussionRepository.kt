package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.PagedList
import io.reactivex.Single

interface DiscussionRepository {
    fun getDiscussionDetails(discussionId: Long): Single<Discussion>
    fun getDiscussions(forumId: String): Single<PagedList<Discussion>>
    fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>>
    fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>>
}