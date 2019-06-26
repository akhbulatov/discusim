package com.akhbulatov.discusim.domain.channel

import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import javax.inject.Inject

class ChannelInteractor @Inject constructor(
    private val forumRepository: ForumRepository
){

    fun getChannelDetails(forumId: String) = forumRepository.getForumDetails(forumId)
}