package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val userResponseMapper: UserResponseMapper
) : UsersRepository {

    override fun getUser(userId: Long): Single<User> =
        api.getUser(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}