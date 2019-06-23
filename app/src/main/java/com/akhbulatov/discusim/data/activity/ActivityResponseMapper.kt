package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.comment.CommentResponseMapper
import com.akhbulatov.discusim.data.forum.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.akhbulatov.discusim.data.thread.ThreadResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Action
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityResponseMapper @Inject constructor(
    private val activityResponseParser: ActivityResponseParser,
    private val threadResponseMapper: ThreadResponseMapper,
    private val forumResponseMapper: ForumResponseMapper,
    private val userResponseMapper: UserResponseMapper,
    private val commentResponseMapper: CommentResponseMapper
) {

    fun map(activityBody: ResponseBody): List<Action> {
        val actions = activityResponseParser.parse(activityBody.string())
        return actions.map {
            when (it.obj) {
                is ActionNetModel.ThreadVoteNetModel -> {
                    Action(
                        threadVote = mapThreadVote(it.obj),
                        type = Action.Type.THREAD_VOTE,
                        createdAt = it.createdAt
                    )
                }
                is CommentNetModel -> {
                    Action(
                        comment = commentResponseMapper.map(it.obj),
                        type = Action.Type.POST,
                        createdAt = it.createdAt
                    )
                }
                else -> throw Exception() // TODO
            }
        }
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