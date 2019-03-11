package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.data.global.network.models.ActivityNetModel
import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.domain.global.models.VoteType
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

class UsersResponseMapper @Inject constructor(moshi: Moshi) {
    private val activityMainAdapter = moshi.adapter(ActivityNetModel.MainNet::class.java)
    private val activityPostAdapter = moshi.adapter(ActivityNetModel.PostNet::class.java)

    fun map(response: UsersResponse): List<User> = response.users
    fun map(response: UserDetailsResponse): UserDetails = response.userDetails

    fun map(responseBody: ResponseBody): List<Activity> {
        val activities = arrayListOf<Activity>()
        val root = JSONObject(responseBody.string())
        val responses = root.getJSONArray("response")

        for (i in 0 until responses.length()) {
            val response = responses.getJSONObject(i)
            val jsonResponse = responses[i].toString()
            val type = response.optString("type")

            if (type == "thread_like") {
                val activityMainNetModel = activityMainAdapter.fromJson(jsonResponse)
                // Далее создает domain-модель
                val main = activityMainNetModel!!.common.let {
                    Activity.Main(
                        it.id,
                        when (it.vote) {
                            1 -> VoteType.UPVOTE
                            -1 -> VoteType.DOWNVOTE
                            else -> VoteType.NOT_VOTE
                        },
                        it.thread,
                        it.forum,
                        it.author
                    )
                }
                val activity = Activity(
                    main = main,
                    type = Activity.Type.THREAD_LIKE,
                    createdAt = activityMainNetModel.createdAt
                )
                activities.add(activity)
            } else if (type == "post") {
                val activityPostNetModel = activityPostAdapter.fromJson(jsonResponse)
                val activity = activityPostNetModel!!.let {
                    Activity(
                        post = it.post,
                        type = Activity.Type.POST,
                        createdAt = it.createdAt
                    )
                }
                activities.add(activity)
            }
        }
        return activities
    }

    fun map(response: UserPostsResponse): List<Post> = response.posts
    fun map(response: UserForumsResponse): List<Forum> = response.forums
}