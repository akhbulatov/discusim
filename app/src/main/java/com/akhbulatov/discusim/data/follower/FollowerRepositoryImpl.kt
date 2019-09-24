package com.akhbulatov.discusim.data.follower

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.domain.global.repositories.FollowerRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowerRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val followerResponseMapper: FollowerResponseMapper,
    private val schedulers: SchedulersProvider
) : FollowerRepository {

    override fun getUserFollowers(userId: Long, cursor: String?): Single<PagedList<User>> =
        api.getUserFollowers(userId, cursor)
            .subscribeOn(schedulers.io())
            .map { followerResponseMapper.map(it) }
            .observeOn(schedulers.computation())
}
