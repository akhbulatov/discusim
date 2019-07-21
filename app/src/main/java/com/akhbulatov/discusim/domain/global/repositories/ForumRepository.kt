package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import io.reactivex.Completable
import io.reactivex.Single

interface ForumRepository {
    fun getForumDetails(forumId: String): Single<ForumDetails>

    fun getMyFollowingForums(cursor: String?): Single<PagedList<Forum>>
    fun getUserFollowingForums(userId: Long, cursor: String?): Single<PagedList<Forum>>

    fun followForum(forumId: String): Completable
    fun unfollowForum(forumId: String): Completable
}