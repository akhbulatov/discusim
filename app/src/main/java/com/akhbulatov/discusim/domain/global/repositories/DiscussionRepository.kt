package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Vote
import io.reactivex.Single

interface DiscussionRepository {
    fun getDiscussionDetails(discussionId: Long): Single<Discussion>

    fun getDiscussions(forumId: String, cursor: String?): Single<PagedList<Discussion>>
    fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>>
    fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>>

    fun voteDiscussion(discussionId: Long, voteType: Vote.Type): Single<Vote>
}