package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import io.reactivex.Single

interface FollowingRepository {
    fun getUserFollowing(userId: Long, cursor: String?): Single<PagedList<UserMiddle>>
}