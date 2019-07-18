package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getMyDetails(): Single<User>
    fun getUserDetails(userId: Long): Single<User>

    fun followUser(userId: Long): Completable
    fun unfollowUser(userId: Long): Completable
}