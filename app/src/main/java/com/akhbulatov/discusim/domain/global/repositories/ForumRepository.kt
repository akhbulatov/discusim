package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Forum
import io.reactivex.Single

interface ForumRepository {
    fun getForumDetails(forumId: String): Single<Forum>
    fun getMyFollowingForums(page: String?): Single<List<Forum>>
}