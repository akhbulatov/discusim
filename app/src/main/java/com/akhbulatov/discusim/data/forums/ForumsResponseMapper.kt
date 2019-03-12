package com.akhbulatov.discusim.data.forums

import com.akhbulatov.discusim.domain.global.models.Forum
import javax.inject.Inject

class ForumsResponseMapper @Inject constructor() {
    fun map(response: ForumResponse): Forum = response.forum
}