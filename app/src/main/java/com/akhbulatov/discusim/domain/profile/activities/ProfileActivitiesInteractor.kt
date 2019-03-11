package com.akhbulatov.discusim.domain.profile.activities

import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileActivitiesInteractor @Inject constructor(
    private val usersRepository: UsersRepository
){

    fun getActivities(userId: Long): Single<List<Activity>> = usersRepository.getUserActivities(userId)
}