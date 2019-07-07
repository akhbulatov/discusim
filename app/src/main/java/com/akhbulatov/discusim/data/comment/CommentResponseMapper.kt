package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.discussion.DiscussionResponseMapper
import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.global.network.models.CommentPreviewNetModel
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.CommentPreview
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.VoteType
import javax.inject.Inject

class CommentResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val discussionResponseMapper: DiscussionResponseMapper
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
                it.likes,
                when (it.userScore) {
                    -1 -> VoteType.DOWNVOTE
                    0 -> VoteType.NO_VOTE
                    else -> VoteType.UPVOTE
                },
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