package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.comment.CommentResponseMapper
import com.akhbulatov.discusim.data.forum.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.global.network.models.CursorNetModel
import com.akhbulatov.discusim.data.thread.ThreadResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Action
import okhttp3.ResponseBody
import javax.inject.Inject

class ActivityResponseMapper @Inject constructor(
    private val activityResponseParser: ActivityResponseParser,
    private val threadResponseMapper: ThreadResponseMapper,
    private val forumResponseMapper: ForumResponseMapper,
    private val userResponseMapper: UserResponseMapper,
    private val commentResponseMapper: CommentResponseMapper
) {

    fun map(activityBody: ResponseBody): Pair<CursorNetModel, List<Action>> {
        val activity = activityResponseParser.parse(activityBody.string())
        val actions = activity.second.map {
            when (it.obj) {
                is ActionNetModel.ThreadVoteNetModel -> {
                    val threadVote = mapThreadVote(it.obj)

                    Action(
                        id = threadVote.id,
                        threadVote = threadVote,
                        type = Action.Type.THREAD_VOTE,
                        createdAt = it.createdAt
                    )
                }
                is CommentNetModel -> {
                    val comment = commentResponseMapper.map(it.obj)

                    Action(
                        id = comment.id,
                        comment = comment,
                        type = Action.Type.COMMENT,
                        createdAt = it.createdAt
                    )
                }
                else -> throw Exception() // TODO
            }
        }
        return Pair(activity.first, actions)
    }

    private fun mapThreadVote(model: ActionNetModel.ThreadVoteNetModel): Action.ThreadVote =
        model.let {
            Action.ThreadVote(
                it.id.toLong(),
                threadResponseMapper.map(it.thread),
                forumResponseMapper.map(it.forum),
                userResponseMapper.map(it.author)
            )
        }
}