package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.thread.ThreadResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Comment
import javax.inject.Inject

class CommentResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val threadResponseMapper: ThreadResponseMapper
) {

    fun map(model: CommentNetModel): Comment =
        model.let {
            Comment(
                it.id.toLong(),
                it.message,
                userResponseMapper.map(it.author),
                threadResponseMapper.map(it.thread)
            )
        }
}