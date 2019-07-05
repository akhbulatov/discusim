package com.akhbulatov.discusim.domain.activity

import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ActivityInteractor @Inject constructor(
    private val activityRepository: ActivityRepository
) {

    fun getMyActivity(cursor: String?): Single<PagedList<Action>> =
        activityRepository.getMyActivity(cursor)

    fun getUserActivity(userId: Long, cursor: String?): Single<PagedList<Action>> =
        activityRepository.getUserActivity(userId, cursor)
}