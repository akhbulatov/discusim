package com.akhbulatov.discusim.domain.profile.followers

import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileFollowersInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    fun getFollowers(userId: Long): Single<List<User>> = usersRepository.getFollowers(userId)
}