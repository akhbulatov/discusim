package com.akhbulatov.discusim.domain.discussion

import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.VoteType
import com.akhbulatov.discusim.domain.global.repositories.DiscussionRepository
import io.reactivex.Single
import javax.inject.Inject

class DiscussionInteractor @Inject constructor(
    private val discussionRepository: DiscussionRepository
) {

    fun getDiscussionDetails(discussionId: Long): Single<Discussion> =
        discussionRepository.getDiscussionDetails(discussionId)

    fun getDiscussions(forumId: String): Single<PagedList<Discussion>> =
        discussionRepository.getDiscussions(forumId)

    fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>> =
        discussionRepository.getHotDiscussions(forumId)

    fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>> =
        discussionRepository.getPopularDiscussions(forumId)

    fun voteDiscussion(discussionId: Long, voteType: VoteType): Single<VoteType> =
        discussionRepository.voteDiscussion(discussionId, voteType)
}