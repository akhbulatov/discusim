package com.akhbulatov.discusim.domain.moderator

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import com.akhbulatov.discusim.domain.global.repositories.ModeratorRepository
import io.reactivex.Single
import javax.inject.Inject

class ModeratorInteractor @Inject constructor(
    private val moderatorRepository: ModeratorRepository
) {

    fun getForumModerators(forumId: String): Single<PagedList<UserMiddle>> =
        moderatorRepository.getForumModerators(forumId)
}