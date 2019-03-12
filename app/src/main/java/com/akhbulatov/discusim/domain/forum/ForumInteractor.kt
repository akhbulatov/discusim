package com.akhbulatov.discusim.domain.forum

import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import javax.inject.Inject

class ForumInteractor @Inject constructor(
    private val forumsRepository: ForumsRepository
){

    fun getForumDetails(forumId: String) = forumsRepository.getForumDetails(forumId)
}