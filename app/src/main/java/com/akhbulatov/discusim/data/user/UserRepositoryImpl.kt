package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val userResponseMapper: UserResponseMapper,
    private val schedulers: SchedulersProvider
) : UserRepository {

    override fun getUserDetails(userId: Long): Single<User> =
        api.getUserDetails(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}