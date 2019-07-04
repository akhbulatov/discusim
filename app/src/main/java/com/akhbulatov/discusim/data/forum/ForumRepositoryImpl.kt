package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val forumResponseMapper: ForumResponseMapper,
    private val schedulers: SchedulersProvider
) : ForumRepository {

    override fun getMyFollowingForums(cursor: String?): Single<PagedList<Forum>> =
        api.getFollowingForums(
            null,
            cursor,
            listOf(
                RequestParams.Forum.FOLLOWS_FORUM,
                RequestParams.Forum.COUNTERS
            )
        )
            .map { forumResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getForumDetails(forumId: String): Single<Forum> =
        api.getForumDetails(
            forumId,
            listOf(
                RequestParams.Forum.FOLLOWS_FORUM,
                RequestParams.Forum.COUNTERS
            )
        )
            .map { forumResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}