package com.akhbulatov.discusim.domain.profile.posts

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfilePostsInteractor @Inject constructor(
    private val userRepository: UserRepository
){

    fun getPosts(userId: Long): Single<List<Comment>> = userRepository.getUserPosts(userId)
}