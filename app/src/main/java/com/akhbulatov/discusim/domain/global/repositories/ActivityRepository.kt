package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Action
import io.reactivex.Single

interface ActivityRepository {
    fun getUserActivity(userId: Long): Single<List<Action>>
}