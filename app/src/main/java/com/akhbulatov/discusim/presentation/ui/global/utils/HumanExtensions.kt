package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.res.Resources
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.global.models.VoteType
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}

private val DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

fun LocalDateTime.getHumanTime(resources: Resources): String {
    val duration = Duration.between(this, LocalDateTime.now())
    return when {
        duration.seconds < 60 -> {
            val seconds = duration.seconds.toInt()
            resources.getQuantityString(R.plurals.time_seconds, seconds, seconds)
        }
        duration.toMinutes() < 60 -> {
            val minutes = duration.toMinutes().toInt()
            resources.getQuantityString(R.plurals.time_minutes, minutes, minutes)
        }
        duration.toHours() < 24 -> {
            val hours = duration.toHours().toInt()
            resources.getQuantityString(R.plurals.time_hours, hours, hours)
        }
        duration.toDays() <= 7 -> {
            val days = duration.toDays().toInt()
            resources.getQuantityString(R.plurals.time_days, days, days)
        }
        else -> this.format(DATE_FORMATTER)
    }
}

fun VoteType.getHumanName(resources: Resources): String = when (this) {
    VoteType.UPVOTE -> resources.getString(R.string.item_user_activity_upvote)
    VoteType.DOWNVOTE -> resources.getString(R.string.item_user_activity_downvote)
    VoteType.NO_VOTE -> resources.getString(R.string.item_user_activity_no_vote)
}