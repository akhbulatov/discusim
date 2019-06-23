package com.akhbulatov.discusim.domain.forums

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumsInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getFollowingForums(userId: Long): Single<List<Forum>> = userRepository.getFollowingForums(userId)
}