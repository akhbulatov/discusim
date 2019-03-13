package com.akhbulatov.discusim.domain.channel

import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import javax.inject.Inject

class ChannelInteractor @Inject constructor(
    private val forumsRepository: ForumsRepository
){

    fun getChannelDetails(forumId: String) = forumsRepository.getForumDetails(forumId)
}