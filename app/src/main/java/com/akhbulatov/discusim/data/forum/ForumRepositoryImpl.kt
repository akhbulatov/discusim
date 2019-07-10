package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val forumResponseMapper: ForumResponseMapper,
    private val schedulers: SchedulersProvider
) : ForumRepository {

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

    override fun getUserFollowingForums(userId: Long, cursor: String?): Single<PagedList<Forum>> =
        api.getFollowingForums(
            userId,
            cursor,
            listOf(
                RequestParams.Forum.FOLLOWS_FORUM,
                RequestParams.Forum.COUNTERS
            )
        )
            .map { forumResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun followForum(forumId: String): Completable =
        api.followForum(forumId)
            .subscribeOn(schedulers.io())

    override fun unfollowForum(forumId: String): Completable =
        api.unfollowForum(forumId)
            .subscribeOn(schedulers.io())
}