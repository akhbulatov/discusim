package com.akhbulatov.discusim.domain.profile.comments

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileCommentsInteractor @Inject constructor(
    private val usersRepository: UsersRepository
){

    fun getComments(userId: Long): Single<List<Comment>> = usersRepository.getUserComments(userId)
}