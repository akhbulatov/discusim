package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.eventbus.CursorStore
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import io.reactivex.Single
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val activityResponseMapper: ActivityResponseMapper,
    private val schedulers: SchedulersProvider,
    private val cursorStore: CursorStore
) : ActivityRepository {

    override fun getMyActivity(page: String?): Single<List<Action>> =
        api.getUserActivity(null, page)
            .map {
                val activity = activityResponseMapper.map(it)
                activity.first.next?.let { next -> cursorStore.publish(next) }
                activity.second
            }
            .subscribeOn(schedulers.io())
}