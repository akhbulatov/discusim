package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.ActionNetModelJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel_ThreadVoteNetModelJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.CommentNetModelJsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityResponseParser @Inject constructor(moshi: Moshi) {
    private val actionAdapter = ActionNetModelJsonAdapter(moshi)
    private val threadVoteAdapter = ActionNetModel_ThreadVoteNetModelJsonAdapter(moshi)
    private val commentAdapter = CommentNetModelJsonAdapter(moshi)

    fun parse(activityJson: String): List<ActionNetModel> {
        val actions = arrayListOf<ActionNetModel>()
        val root = JSONObject(activityJson)
        val responses = root.getJSONArray("response")

        for (i in 0 until responses.length()) {
            val actionJson = responses.getJSONObject(i)
            val objJson = actionJson.getJSONObject("object").toString()
            val action = actionAdapter.fromJson(actionJson.toString())!!

            val obj: Any? = when (action.type) {
                ActivityType.THREAD_LIKE.type -> threadVoteAdapter.fromJson(objJson)!!
                ActivityType.POST.type -> commentAdapter.fromJson(objJson)
                else -> null
            }

            val finalAction = action.copy(obj = obj)
            actions.add(finalAction)
        }
        return actions
    }

    private enum class ActivityType(val type: String) {
        THREAD_LIKE("thread_like"),
        POST("post")
    }
}