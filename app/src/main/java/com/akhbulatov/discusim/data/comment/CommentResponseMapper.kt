package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.discussion.DiscussionResponseMapper
import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.global.network.models.CommentPreviewNetModel
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.CommentPreview
import com.akhbulatov.discusim.domain.global.models.PagedList
import javax.inject.Inject

class CommentResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val discussionResponseMapper: DiscussionResponseMapper,
    private val voteResponseMapper: VoteResponseMapper
) {

    fun map(response: CommentsResponse): PagedList<Comment> {
        val comments = response.comments.map { map(it) }
        return PagedList(response.cursor?.next, comments)
    }

    fun map(model: CommentNetModel): Comment =
        model.let {
            Comment(
                it.id.toLong(),
                it.message,
                userResponseMapper.map(it.author),
                it.createdAt,
                voteResponseMapper.map(it.likes, it.userScore),
                discussionResponseMapper.map(it.thread)
            )
        }

    fun map(model: CommentPreviewNetModel): CommentPreview =
        model.let {
            CommentPreview(
                it.id.toLong(),
                it.message,
                userResponseMapper.map(it.author),
                discussionResponseMapper.map(it.thread)
            )
        }
}