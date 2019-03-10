package com.akhbulatov.discusim.domain.profile.following

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileFollowingInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    fun getFollowingUsers(userId: Long): Single<List<User>> = usersRepository.getFollowingUsers(userId)
    fun getFollowingForums(userId: Long): Single<List<Forum>> = usersRepository.getFollowingForums(userId)
}