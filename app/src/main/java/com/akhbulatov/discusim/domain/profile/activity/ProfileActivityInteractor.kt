package com.akhbulatov.discusim.domain.profile.activity

import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileActivityInteractor @Inject constructor(
    private val activityRepository: ActivityRepository
) {

    fun getActivity(userId: Long): Single<List<Action>> = activityRepository.getUserActivity(userId)
}