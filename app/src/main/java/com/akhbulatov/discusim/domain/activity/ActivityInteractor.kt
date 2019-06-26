package com.akhbulatov.discusim.domain.activity

import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ActivityInteractor @Inject constructor(
    private val activityRepository: ActivityRepository
) {

    fun getUserActivity(userId: Long, page: String?): Single<List<Action>> =
        activityRepository.getUserActivity(userId, page)
}