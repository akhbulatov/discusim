package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.PagedList
import io.reactivex.Single

interface ActivityRepository {
    fun getMyActivity(cursor: String?): Single<PagedList<Action>>
    fun getUserActivity(userId: Long, cursor: String?): Single<PagedList<Action>>
}
