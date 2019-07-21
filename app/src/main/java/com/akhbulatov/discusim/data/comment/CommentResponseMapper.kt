package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.discussion.DiscussionResponseMapper
import com.akhbulatov.discusim.data.forum.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.comment.CommentNetModel
import com.akhbulatov.discusim.data.global.network.models.comment.CommentShortNetModel
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.comment.Comment
import com.akhbulatov.discusim.domain.global.models.comment.CommentShort
import com.akhbulatov.discusim.domain.global.models.PagedList
import javax.inject.Inject

class CommentResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val discussionResponseMapper: DiscussionResponseMapper,
    private val forumResponseMapper: ForumResponseMapper,
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

    fun map(model: CommentShortNetModel): CommentShort =
        model.let {
            CommentShort(
                it.id.toLong(),
                it.message,
                userResponseMapper.map(it.author),
                discussionResponseMapper.map(it.thread),
                forumResponseMapper.map(it.forum)
            )
        }
}