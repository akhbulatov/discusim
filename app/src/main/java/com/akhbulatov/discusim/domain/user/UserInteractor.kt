package com.akhbulatov.discusim.domain.user

import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getUserDetails(userId: Long): Single<User> =
        userRepository.getUserDetails(userId)
}