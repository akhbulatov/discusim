package com.akhbulatov.discusim.domain.followitems

import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class FollowItemsInteractor @Inject constructor(
    private val usersRepository: UsersRepository,
    private val forumsRepository: ForumsRepository
) {

    fun getFollowingUsers(userId: Long): Single<List<User>> = usersRepository.getFollowingUsers(userId)

    fun getFollowingForums(userId: Long): Single<List<Forum>> = usersRepository.getFollowingForums(userId)

    fun getFollowers(userId: Long): Single<List<User>> = usersRepository.getFollowers(userId)

    fun getTopCommenters(forumId: String): Single<List<User>> = forumsRepository.getTopCommenters(forumId)
}