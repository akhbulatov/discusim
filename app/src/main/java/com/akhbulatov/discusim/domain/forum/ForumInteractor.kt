package com.akhbulatov.discusim.domain.forum

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ForumInteractor @Inject constructor(
    private val forumRepository: ForumRepository
) {

    fun getForumDetails(forumId: String): Single<ForumDetails> =
        forumRepository.getForumDetails(forumId)

    fun getMyFollowingForums(cursor: String?): Single<PagedList<Forum>> =
        forumRepository.getMyFollowingForums(cursor)

    fun getUserFollowingForums(userId: Long, cursor: String?): Single<PagedList<Forum>> =
        forumRepository.getUserFollowingForums(userId, cursor)

    fun followForum(forumId: String): Completable =
        forumRepository.followForum(forumId)

    fun unfollowForum(forumId: String): Completable =
        forumRepository.unfollowForum(forumId)
}