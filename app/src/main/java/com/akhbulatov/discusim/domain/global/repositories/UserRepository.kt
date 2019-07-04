package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.User
import io.reactivex.Single

interface UserRepository {
    fun getUserDetails(userId: Long): Single<User>
}