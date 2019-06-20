package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val activityResponseMapper: ActivityResponseMapper,
    private val schedulers: SchedulersProvider
) : ActivityRepository {

    override fun getUserActivity(userId: Long): Single<List<Action>> =
        api.getUserActivity(userId)
            .map { activityResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}