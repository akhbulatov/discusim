package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.global.models.User
import io.reactivex.Single

interface ForumRepository {
    fun getMyFollowingForums(page: String?): Single<List<Forum>>

    fun getForumDetails(forumId: String): Single<Forum>
    fun getThreads(forumId: String): Single<List<Thread>>
    fun getTopCommenters(forumId: String): Single<List<User>>
    fun getModerators(forumId: String): Single<List<User>>
}