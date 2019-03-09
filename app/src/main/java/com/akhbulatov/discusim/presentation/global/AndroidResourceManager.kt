package com.akhbulatov.discusim.presentation.global

import android.content.Context
import com.akhbulatov.discusim.domain.global.ResourceManager
import javax.inject.Inject

class AndroidResourceManager @Inject constructor(private val context: Context) : ResourceManager {
    override fun getString(resId: Int): String = context.getString(resId)
}