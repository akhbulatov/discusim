package com.akhbulatov.discusim.data.following

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.domain.global.repositories.FollowingRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowingRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val followingResponseMapper: FollowingResponseMapper,
    private val schedulers: SchedulersProvider
) : FollowingRepository {

    override fun getUserFollowing(userId: Long, cursor: String?): Single<PagedList<User>> =
        api.getUserFollowing(userId, cursor)
            .map { followingResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}
