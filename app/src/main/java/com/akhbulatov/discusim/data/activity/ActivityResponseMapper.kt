package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.forums.ForumResponseMapper
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.post.PostResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.VoteType
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityResponseMapper @Inject constructor(
    moshi: Moshi,
    private val forumResponseMapper: ForumResponseMapper,
    private val userResponseMapper: UserResponseMapper,
    private val postResponseMapper: PostResponseMapper
) {

    private val threadVoteContainerAdapter = moshi.adapter(ActionNetModel.ThreadVoteContainer::class.java)
    private val postAdapter = moshi.adapter(ActionNetModel.PostNet::class.java)

    fun map(actionsBody: ResponseBody): List<Action> {
        val actions = arrayListOf<Action>()
        val root = JSONObject(actionsBody.string())
        val responses = root.getJSONArray("response")

        for (i in 0 until responses.length()) {
            val response = responses.getJSONObject(i)
            val jsonResponse = responses[i].toString()
            val type = response.optString("type")

            if (type == "thread_like") {
                val action = parseThreadLike(jsonResponse)
                actions.add(action)
            } else if (type == "post") {
                val action = parsePost(jsonResponse)
                actions.add(action)
            }
        }
        return actions
    }

    private fun parseThreadLike(jsonResponse: String): Action {
        val threadVoteContainer = threadVoteContainerAdapter.fromJson(jsonResponse)
        // Далее создается domain-модель
        val threadVote = threadVoteContainer!!.threadVote.let {
            Action.ThreadVote(
                it.id,
                when (it.vote) {
                    1 -> VoteType.UPVOTE
                    -1 -> VoteType.DOWNVOTE
                    else -> VoteType.NOT_VOTE
                },
                parseThread(it.thread),
                forumResponseMapper.map(it.forum),
                userResponseMapper.map(it.author)
            )
        }
        return Action(
            threadVote = threadVote,
            type = Action.Type.THREAD_LIKE,
            createdAt = threadVoteContainer.createdAt
        )
    }

    private fun parseThread(thread: ActionNetModel.ThreadVoteContainer.ThreadVoteNet.ThreadNet) =
        thread.let {
            Action.ThreadVote.Thread(
                it.id.toLong(),
                it.title
            )
        }

    private fun parsePost(jsonResponse: String): Action {
        val postNetModel = postAdapter.fromJson(jsonResponse)
        return postNetModel!!.let {
            Action(
                post = postResponseMapper.map(it.post),
                type = Action.Type.POST,
                createdAt = it.createdAt
            )
        }
    }
}