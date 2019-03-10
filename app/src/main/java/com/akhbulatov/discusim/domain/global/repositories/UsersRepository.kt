package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.User
import io.reactivex.Single

interface UsersRepository {
    fun getUser(userId: Long): Single<User>
}