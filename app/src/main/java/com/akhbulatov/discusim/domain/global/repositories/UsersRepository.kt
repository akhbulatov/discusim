package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.UserDetails
import io.reactivex.Single

interface UsersRepository {
    fun getUser(userId: Long): Single<UserDetails>
    fun getUserComments(userId: Long): Single<List<Comment>>
}