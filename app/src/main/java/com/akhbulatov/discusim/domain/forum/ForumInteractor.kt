package com.akhbulatov.discusim.domain.forum

import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import javax.inject.Inject

class ForumInteractor @Inject constructor(
    private val forumRepository: ForumRepository
) {

    fun getMyFollowingForums(page: String?) =
        forumRepository.getMyFollowingForums(page)

    fun getForumDetails(forumId: String) = forumRepository.getForumDetails(forumId)
}