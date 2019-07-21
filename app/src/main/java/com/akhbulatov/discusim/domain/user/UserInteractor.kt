package com.akhbulatov.discusim.domain.user

import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getMyDetails(): Single<UserDetails> =
        userRepository.getMyDetails()

    fun getUserDetails(userId: Long): Single<UserDetails> =
        userRepository.getUserDetails(userId)

    fun followUser(userId: Long): Completable =
        userRepository.followUser(userId)

    fun unfollowUser(userId: Long): Completable =
        userRepository.unfollowUser(userId)
}