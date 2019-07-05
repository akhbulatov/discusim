package com.akhbulatov.discusim.domain.following

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import com.akhbulatov.discusim.domain.global.repositories.FollowingRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowingInteractor @Inject constructor(
    private val followingRepository: FollowingRepository
) {

    fun getUserFollowing(userId: Long, cursor: String?): Single<PagedList<UserMiddle>> =
        followingRepository.getUserFollowing(userId, cursor)
}