package com.akhbulatov.discusim.domain.channeldetails

import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import javax.inject.Inject

class ChannelDetailsInteractor @Inject constructor(
    private val forumsRepository: ForumsRepository
){

    fun getChannelDetails(forumId: String) = forumsRepository.getForumDetails(forumId)
}