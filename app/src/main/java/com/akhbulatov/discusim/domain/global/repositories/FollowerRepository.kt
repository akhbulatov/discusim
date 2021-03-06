package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import io.reactivex.Single

interface FollowerRepository {
    fun getUserFollowers(userId: Long, cursor: String?): Single<PagedList<User>>
}
