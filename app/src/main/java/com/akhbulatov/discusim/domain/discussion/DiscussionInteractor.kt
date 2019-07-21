package com.akhbulatov.discusim.domain.discussion

import com.akhbulatov.discusim.domain.global.models.discussion.Discussion
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.repositories.DiscussionRepository
import io.reactivex.Single
import javax.inject.Inject

class DiscussionInteractor @Inject constructor(
    private val discussionRepository: DiscussionRepository
) {

    fun getDiscussionDetails(discussionId: Long): Single<Discussion> =
        discussionRepository.getDiscussionDetails(discussionId)

    fun getDiscussions(forumId: String, cursor: String?): Single<PagedList<Discussion>> =
        discussionRepository.getDiscussions(forumId, cursor)

    fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>> =
        discussionRepository.getHotDiscussions(forumId)

    fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>> =
        discussionRepository.getPopularDiscussions(forumId)

    fun voteDiscussion(discussionId: Long, voteType: Vote.Type): Single<Vote> =
        discussionRepository.voteDiscussion(discussionId, voteType)
}