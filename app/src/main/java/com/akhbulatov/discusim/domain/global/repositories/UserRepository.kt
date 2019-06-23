package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import io.reactivex.Single

interface UserRepository {
    fun getUser(userId: Long): Single<UserDetails>

    fun getUserPosts(userId: Long): Single<List<Comment>>

    fun getFollowers(userId: Long): Single<List<User>>
    fun getFollowingUsers(userId: Long): Single<List<User>>
    fun getFollowingForums(userId: Long): Single<List<Forum>>
}