package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import io.reactivex.Single

interface ForumRepository {
    fun getForumDetails(forumId: String): Single<Forum>
    fun getMyFollowingForums(page: String?): Single<List<Forum>>

    fun getTopCommenters(forumId: String): Single<List<User>>
    fun getModerators(forumId: String): Single<List<User>>
}