package com.akhbulatov.discusim.domain.profile

import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    fun getProfile(userId: Long): Single<User> = usersRepository.getUser(userId)
}