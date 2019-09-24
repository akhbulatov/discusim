package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val userResponseMapper: UserResponseMapper,
    private val schedulers: SchedulersProvider
) : UserRepository {

    override fun getMyDetails(): Single<UserDetails> =
        api.getUserDetails(null)
            .subscribeOn(schedulers.io())
            .map { userResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun getUserDetails(userId: Long): Single<UserDetails> =
        api.getUserDetails(userId)
            .subscribeOn(schedulers.io())
            .map { userResponseMapper.map(it) }
            .observeOn(schedulers.computation())

    override fun followUser(userId: Long): Completable =
        api.followUser(userId)
            .subscribeOn(schedulers.io())

    override fun unfollowUser(userId: Long): Completable =
        api.unfollowUser(userId)
            .subscribeOn(schedulers.io())
}
