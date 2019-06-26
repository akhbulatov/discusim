package com.akhbulatov.discusim.domain.channeldetails

import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import javax.inject.Inject

class ChannelDetailsInteractor @Inject constructor(
    private val forumRepository: ForumRepository
){

    fun getChannelDetails(forumId: String) = forumRepository.getForumDetails(forumId)
}