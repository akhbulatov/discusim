package com.akhbulatov.discusim.domain.follower

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.domain.global.repositories.FollowerRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowerInteractor @Inject constructor(
    private val followerRepository: FollowerRepository
) {

    fun getUserFollowers(userId: Long, cursor: String?): Single<PagedList<User>> =
        followerRepository.getUserFollowers(userId, cursor)
}
