package com.akhbulatov.discusim.domain.forum

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumInteractor @Inject constructor(
    private val forumRepository: ForumRepository
) {

    fun getForumDetails(forumId: String): Single<Forum> =
        forumRepository.getForumDetails(forumId)

    fun getMyFollowingForums(page: String?): Single<List<Forum>> =
        forumRepository.getMyFollowingForums(page)
}