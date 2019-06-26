package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val userResponseMapper: UserResponseMapper
) : UserRepository {

    override fun getUser(userId: Long): Single<UserDetails> =
        api.getUser(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getUserPosts(userId: Long): Single<List<Comment>> =
        api.getUserPosts(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowers(userId: Long): Single<List<User>> =
        api.getFollowers(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowingUsers(userId: Long): Single<List<User>> =
        api.getFollowingUsers(userId)
            .map { userResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}