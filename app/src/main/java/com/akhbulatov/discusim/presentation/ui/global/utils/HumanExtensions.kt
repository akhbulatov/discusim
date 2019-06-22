package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.res.Resources
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.global.models.VoteType
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}

fun VoteType.getHumanName(res: Resources): String = when (this) {
    VoteType.UPVOTE -> res.getString(R.string.item_user_activity_upvote)
    VoteType.DOWNVOTE -> res.getString(R.string.item_user_activity_downvote)
    VoteType.NO_VOTE -> res.getString(R.string.item_user_activity_no_vote)
}