package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.UserDetails
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getMyDetails(): Single<UserDetails>
    fun getUserDetails(userId: Long): Single<UserDetails>

    fun followUser(userId: Long): Completable
    fun unfollowUser(userId: Long): Completable
}