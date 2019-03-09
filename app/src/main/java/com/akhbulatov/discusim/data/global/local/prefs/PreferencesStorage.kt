package com.akhbulatov.discusim.data.global.local.prefs

import android.content.Context
import javax.inject.Inject

interface PreferencesStorage {
}

class SharedPreferencesStorage @Inject constructor(context: Context) : PreferencesStorage {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "discusim.prefs"
    }
}