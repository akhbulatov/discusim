package com.akhbulatov.discusim.data.activity

import com.akhbulatov.discusim.data.global.network.models.ActionNetModel
import com.akhbulatov.discusim.data.global.network.models.ActionNetModelJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.ActionNetModel_DiscussionVoteNetModelJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.comment.CommentShortNetModelJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.cursor.CursorResponseJsonAdapter
import com.akhbulatov.discusim.data.global.network.models.cursor.PagedListNetModel
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

class ActivityResponseParser @Inject constructor(moshi: Moshi) {
    private val cursorResponseAdapter = CursorResponseJsonAdapter(moshi)
    private val actionAdapter = ActionNetModelJsonAdapter(moshi)
    private val discussionVoteAdapter = ActionNetModel_DiscussionVoteNetModelJsonAdapter(moshi)
    private val commentAdapter = CommentShortNetModelJsonAdapter(moshi)

    fun parse(activityBody: ResponseBody): PagedListNetModel<ActionNetModel> {
        val activityJson = activityBody.string()
        val cursor = cursorResponseAdapter.fromJson(activityJson)!!.cursor
        val actions = arrayListOf<ActionNetModel>()

        val root = JSONObject(activityJson)
        val responses = root.getJSONArray("response")

        for (i in 0 until responses.length()) {
            val actionJson = responses.getJSONObject(i)
            val objJson = actionJson.getJSONObject("object").toString()
            val action = actionAdapter.fromJson(actionJson.toString())!!

            val obj: Any? = when (action.type) {
                ActivityType.THREAD_LIKE.type -> discussionVoteAdapter.fromJson(objJson)!!
                ActivityType.POST.type,
                ActivityType.REPLY.type -> commentAdapter.fromJson(objJson)
                else -> null
            }

            val finalAction = action.copy(obj = obj)
            actions.add(finalAction)
        }
        return PagedListNetModel(cursor!!, actions)
    }

    private enum class ActivityType(val type: String) {
        THREAD_LIKE("thread_like"),
        POST("post"),
        REPLY("reply")
    }
}
