package com.akhbulatov.discusim.domain.users

import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UsersInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val forumRepository: ForumRepository
) {

    fun getFollowingUsers(userId: Long): Single<List<User>> = userRepository.getFollowingUsers(userId)

    fun getFollowers(userId: Long): Single<List<User>> = userRepository.getFollowers(userId)

    fun getTopCommenters(forumId: String): Single<List<User>> = forumRepository.getTopCommenters(forumId)

    fun getModerators(forumId: String): Single<List<User>> = forumRepository.getModerators(forumId)
}