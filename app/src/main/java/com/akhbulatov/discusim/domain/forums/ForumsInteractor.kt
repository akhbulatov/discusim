package com.akhbulatov.discusim.domain.forums

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ForumsInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    fun getFollowingForums(userId: Long): Single<List<Forum>> = usersRepository.getFollowingForums(userId)
}