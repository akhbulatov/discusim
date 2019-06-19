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

fun VoteType.getHumanName(resources: Resources): String = when (this) {
    // TODO Взять строки из ресурсов
    VoteType.UPVOTE -> "upvotes"
    VoteType.DOWNVOTE -> "downvotes"
    VoteType.NOT_VOTE -> "not votes"
}