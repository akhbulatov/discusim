package com.akhbulatov.discusim.domain.users

import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val forumsRepository: ForumsRepository
) {

    fun getFollowingUsers(userId: Long): Single<List<User>> = usersRepository.getFollowingUsers(userId)

    fun getFollowers(userId: Long): Single<List<User>> = usersRepository.getFollowers(userId)

    fun getTopCommenters(forumId: String): Single<List<User>> = forumsRepository.getTopCommenters(forumId)
}