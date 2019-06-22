package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.CommentNetModel
import com.squareup.moshi.Moshi
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityResponseParser @Inject constructor(moshi: Moshi) {
    private val actionAdapter = moshi.adapter(ActionNetModel::class.java)
    private val threadVoteAdapter = moshi.adapter(ActionNetModel.ThreadVoteNet::class.java)
    private val commentAdapter = moshi.adapter(CommentNetModel::class.java)

    fun parse(activityJson: String): List<ActionNetModel> {
        val actions = arrayListOf<ActionNetModel>()
        val root = JSONObject(activityJson)
        val responses = root.getJSONArray("response")

        for (i in 0 until responses.length()) {
            val actionJson = responses.getJSONObject(i)
            val objectJson = actionJson.getJSONObject("object").toString()
            val action = actionAdapter.fromJson(actionJson.toString())!!

            val `object`: Any? = when (action.type) {
                ActivityType.THREAD_LIKE.type -> threadVoteAdapter.fromJson(objectJson)!!
                ActivityType.POST.type -> commentAdapter.fromJson(objectJson)
                else -> null
            }

            val finalAction = action.copy(`object` = `object`)
            actions.add(finalAction)
        }
        return actions
    }

    private enum class ActivityType(val type: String) {
        THREAD_LIKE("thread_like"),
        POST("post")
    }
}