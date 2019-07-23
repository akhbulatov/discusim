package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val activityResponseMapper: ActivityResponseMapper,
    private val schedulers: SchedulersProvider
) : ActivityRepository {

    override fun getMyActivity(cursor: String?): Single<PagedList<Action>> =
        api.getUserActivity(null, cursor)
            .map { activityResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getUserActivity(userId: Long, cursor: String?): Single<PagedList<Action>> =
        api.getUserActivity(userId, cursor)
            .map { activityResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}
