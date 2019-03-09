package com.akhbulatov.discusim.presentation.global.utils

import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.ResourceManager
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}