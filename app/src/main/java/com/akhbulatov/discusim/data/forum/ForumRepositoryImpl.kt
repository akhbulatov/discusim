package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.thread.ThreadResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.eventbus.CursorStore
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val forumResponseMapper: ForumResponseMapper,
    private val threadResponseMapper: ThreadResponseMapper,
    private val userResponseMapper: UserResponseMapper,
    private val schedulers: SchedulersProvider,
    private val cursorStore: CursorStore
) : ForumRepository {

    override fun getForumDetails(forumId: String): Single<Forum> =
        api.getForumDetails(forumId)
            .map { forumResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowingForums(userId: Long, page: String?): Single<List<Forum>> =
        api.getFollowingForums(userId, page)
            .doOnSuccess { it.cursor?.next?.let { next -> cursorStore.publish(next) } }
            .map { forumResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getThreads(forumId: String): Single<List<Thread>> =
        api.getThreads(forumId, arrayListOf("forum", "author"))
            .map { threadResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getTopCommenters(forumId: String): Single<List<User>> =
        api.getForumMostActiveUsers(forumId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getModerators(forumId: String): Single<List<User>> =
        api.getForumModerators(forumId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}