package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val schedulers: SchedulersProvider,
    private val usersResponseMapper: UsersResponseMapper
) : UsersRepository {

    override fun getUser(userId: Long): Single<UserDetails> =
        api.getUser(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getUserActivities(userId: Long): Single<List<Activity>> =
        api.getUserActivities(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getUserComments(userId: Long): Single<List<Comment>> =
        api.getUserComments(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowers(userId: Long): Single<List<User>> =
        api.getFollowers(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowingUsers(userId: Long): Single<List<User>> =
        api.getFollowingUsers(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun getFollowingForums(userId: Long): Single<List<Forum>> =
        api.getFollowingForums(userId)
            .map { usersResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}