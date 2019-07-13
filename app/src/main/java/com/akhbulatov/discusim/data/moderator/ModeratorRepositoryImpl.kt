package com.akhbulatov.discusim.data.moderator

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import com.akhbulatov.discusim.domain.global.repositories.ModeratorRepository
import io.reactivex.Single
import javax.inject.Inject

class ModeratorRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val moderatorResponseMapper: ModeratorResponseMapper,
    private val schedulers: SchedulersProvider
) : ModeratorRepository {

    override fun getForumModerators(forumId: String): Single<PagedList<UserMiddle>> =
        api.getModerators(forumId)
            .map { moderatorResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}