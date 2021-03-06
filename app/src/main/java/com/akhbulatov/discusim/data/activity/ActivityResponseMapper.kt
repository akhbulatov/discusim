package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.comment.CommentResponseMapper
import com.akhbulatov.discusim.data.discussion.DiscussionResponseMapper
import com.akhbulatov.discusim.data.forum.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.comment.CommentShortNetModel
import com.akhbulatov.discusim.data.global.network.models.cursor.PagedListNetModel
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.PagedList
import javax.inject.Inject

class ActivityResponseMapper @Inject constructor(
    private val discussionResponseMapper: DiscussionResponseMapper,
    private val forumResponseMapper: ForumResponseMapper,
    private val userResponseMapper: UserResponseMapper,
    private val commentResponseMapper: CommentResponseMapper
) {

    fun map(model: PagedListNetModel<ActionNetModel>): PagedList<Action> {
        val actions = model.data.mapNotNull {
            when (it.obj) {
                is ActionNetModel.DiscussionVoteNetModel -> {
                    val discussionVote = mapDiscussionVote(it.obj)

                    Action(
                        discussionVote = discussionVote,
                        type = Action.Type.DISCUSSION_VOTE,
                        createdAt = it.createdAt
                    )
                }
                is CommentShortNetModel -> {
                    val comment = commentResponseMapper.map(it.obj)
                    val type = if (!comment.hasParent) Action.Type.COMMENT else Action.Type.REPLY

                    Action(
                        comment = comment,
                        type = type,
                        createdAt = it.createdAt
                    )
                }
                else -> null
            }
        }
        return PagedList(model.cursor.next, actions)
    }

    private fun mapDiscussionVote(model: ActionNetModel.DiscussionVoteNetModel): Action.DiscussionVote =
        model.let {
            Action.DiscussionVote(
                it.id.toLong(),
                discussionResponseMapper.map(it.thread),
                forumResponseMapper.map(it.forum),
                userResponseMapper.map(it.author)
            )
        }
}
