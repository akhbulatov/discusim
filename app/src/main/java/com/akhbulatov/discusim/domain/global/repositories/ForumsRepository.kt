package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Thread
import io.reactivex.Single

interface ForumsRepository {
    fun getForumDetails(forumId: String): Single<Forum>
    fun getThreads(forumId: String): Single<List<Thread>>
}