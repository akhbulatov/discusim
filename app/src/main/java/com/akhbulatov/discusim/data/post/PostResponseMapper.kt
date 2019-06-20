package com.akhbulatov.discusim.data.post

import com.akhbulatov.discusim.data.global.network.models.PostNetModel
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(model: PostNetModel): Post =
        model.let {
            Post(
                it.id,
                it.message,
                it.createdAt,
                userResponseMapper.map(it.author),
                it.likes
            )
        }
}