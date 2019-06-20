package com.akhbulatov.discusim.domain.profile

import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getProfile(userId: Long): Single<UserDetails> = userRepository.getUser(userId)
}