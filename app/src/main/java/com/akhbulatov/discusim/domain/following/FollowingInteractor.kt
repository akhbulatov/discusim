package com.akhbulatov.discusim.domain.following

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.FollowingRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowingInteractor @Inject constructor(
    private val followingRepository: FollowingRepository
) {

    fun getUserFollowing(userId: Long, cursor: String?): Single<PagedList<User>> =
        followingRepository.getUserFollowing(userId, cursor)
}