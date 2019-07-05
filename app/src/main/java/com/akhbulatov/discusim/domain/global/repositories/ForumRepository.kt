package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.PagedList
import io.reactivex.Single

interface ForumRepository {
    fun getMyFollowingForums(cursor: String?): Single<PagedList<Forum>>
    fun getUserFollowingForums(userId: Long, cursor: String?): Single<PagedList<Forum>>

    fun getForumDetails(forumId: String): Single<Forum>
}