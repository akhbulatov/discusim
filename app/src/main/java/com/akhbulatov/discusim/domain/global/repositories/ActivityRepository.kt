package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Action
import io.reactivex.Single

interface ActivityRepository {
    fun getMyActivity(page: String?): Single<List<Action>>
}