package com.akhbulatov.discusim.data.global.local.prefs

import android.content.Context
import androidx.core.content.edit
import com.akhbulatov.discusim.domain.global.models.Session
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(context: Context) : PreferencesStorage {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var userId: Long
        get() = prefs.getLong(PREF_USER_ID, -1)
        set(value) = prefs.edit { putLong(PREF_USER_ID, value) }

    override var accessToken: String?
        get() = prefs.getString(PREF_ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(PREF_ACCESS_TOKEN, value) }

    override fun isLoggedIn(): Boolean {
        return userId != -1L && accessToken != null
    }

    override fun setLoggedIn(session: Session) {
        userId = session.userId
        accessToken = session.accessToken
    }

    override fun logout() {
        userId = -1
        accessToken = null
    }

    companion object {
        private const val PREFS_NAME = "discusim.prefs"

        private const val PREF_USER_ID = "user_id"
        private const val PREF_ACCESS_TOKEN = "access_token"
    }
}