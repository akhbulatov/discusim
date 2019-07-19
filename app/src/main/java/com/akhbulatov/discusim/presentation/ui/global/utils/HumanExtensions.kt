package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.res.Resources
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.ResourceManager
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import retrofit2.HttpException
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is HttpException -> when (this.code()) {
        400 -> resourceManager.getString(R.string.error_bad_request)
        500 -> resourceManager.getString(R.string.error_server)
        else -> resourceManager.getString(R.string.error_unknown)
    }
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}

private val MEDIUM_DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

fun LocalDateTime.getHumanCreatedTime(resources: Resources): String {
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
        else -> getMediumDate()
    }
}

fun LocalDateTime.getMediumDate(): String = this.format(MEDIUM_DATE_FORMATTER)