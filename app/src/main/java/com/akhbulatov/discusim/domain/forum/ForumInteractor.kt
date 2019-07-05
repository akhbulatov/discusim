package com.akhbulatov.discusim.domain.forum

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumInteractor @Inject constructor(
    private val forumRepository: ForumRepository
) {

    fun getMyFollowingForums(cursor: String?): Single<PagedList<Forum>> =
        forumRepository.getMyFollowingForums(cursor)

    fun getUserFollowingForums(userId: Long, cursor: String?): Single<PagedList<Forum>> =
        forumRepository.getUserFollowingForums(userId, cursor)

    fun getForumDetails(forumId: String): Single<Forum> =
        forumRepository.getForumDetails(forumId)
}