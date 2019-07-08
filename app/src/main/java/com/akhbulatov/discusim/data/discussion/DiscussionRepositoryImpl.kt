package com.akhbulatov.discusim.data.discussion

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponseMapper
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.repositories.DiscussionRepository
import io.reactivex.Single
import javax.inject.Inject

class DiscussionRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val discussionResponseMapper: DiscussionResponseMapper,
    private val voteResponseMapper: VoteResponseMapper,
    private val schedulers: SchedulersProvider
) : DiscussionRepository {

    override fun getDiscussionDetails(discussionId: Long): Single<Discussion> =
        api.getDiscussionDetails(
            discussionId,
            listOf(RequestParams.Discussion.AUTHOR),
            listOf(RequestParams.Discussion.TOPICS)
        )
            .map { discussionResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getDiscussions(forumId: String): Single<PagedList<Discussion>> =
        api.getDiscussions(
            forumId,
            listOf(RequestParams.Discussion.AUTHOR),
            listOf(RequestParams.Discussion.TOPICS)
        )
            .map { discussionResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>> =
        api.getHotDiscussions(
            forumId,
            listOf(RequestParams.Discussion.AUTHOR)
        )
            .map { discussionResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>> =
        api.getPopularDiscussions(
            forumId,
            listOf(RequestParams.Discussion.AUTHOR)
        )
            .map { discussionResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun voteDiscussion(discussionId: Long, voteType: Vote.Type): Single<Vote> =
        api.voteDiscussion(
            discussionId,
            voteResponseMapper.map(voteType)
        )
            .map { voteResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}