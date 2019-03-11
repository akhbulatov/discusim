package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import io.reactivex.Single

interface UsersRepository {
    fun getUser(userId: Long): Single<UserDetails>

    fun getUserActivities(userId: Long): Single<List<Activity>>

    fun getUserPosts(userId: Long): Single<List<Post>>

    fun getFollowers(userId: Long): Single<List<User>>
    fun getFollowingUsers(userId: Long): Single<List<User>>
    fun getFollowingForums(userId: Long): Single<List<Forum>>
}