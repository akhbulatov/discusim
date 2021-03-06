package com.akhbulatov.discusim.data.discussion

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponseMapper
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.models.discussion.Discussion
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
            .subscribeOn(schedulers.io())
            .map { discussionResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun getDiscussions(forumId: String, cursor: String?): Single<PagedList<Discussion>> =
        api.getDiscussions(
            forumId,
            cursor,
            listOf(RequestParams.Discussion.AUTHOR),
            listOf(RequestParams.Discussion.TOPICS)
        )
            .subscribeOn(schedulers.io())
            .map { discussionResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun getHotDiscussions(forumId: String): Single<PagedList<Discussion>> =
        api.getHotDiscussions(
            forumId,
            listOf(RequestParams.Discussion.AUTHOR)
        )
            .subscribeOn(schedulers.io())
            .map { discussionResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun getPopularDiscussions(forumId: String): Single<PagedList<Discussion>> =
        api.getPopularDiscussions(
            forumId,
            listOf(RequestParams.Discussion.AUTHOR)
        )
            .subscribeOn(schedulers.io())
            .map { discussionResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun voteDiscussion(discussionId: Long, voteType: Vote.Type): Single<Vote.Type> =
        api.voteDiscussion(
            discussionId,
            voteResponseMapper.map(voteType)
        )
            .subscribeOn(schedulers.io())
            .map { voteResponseMapper.map(it) }
            .observeOn(schedulers.computation())
}
