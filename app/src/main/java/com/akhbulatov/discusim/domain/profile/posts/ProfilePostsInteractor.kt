package com.akhbulatov.discusim.domain.profile.posts

import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfilePostsInteractor @Inject constructor(
    private val usersRepository: UsersRepository
){

    fun getPosts(userId: Long): Single<List<Post>> = usersRepository.getUserPosts(userId)
}