package com.akhbulatov.discusim.data.global.local.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : PreferencesStorage {

    override var accessToken: String?
        get() = sharedPrefs.getString(PREF_ACCESS_TOKEN, null)
        set(value) = sharedPrefs.edit { putString(PREF_ACCESS_TOKEN, value) }

    override var refreshToken: String?
        get() = sharedPrefs.getString(PREF_REFRESH_TOKEN, null)
        set(value) = sharedPrefs.edit { putString(PREF_REFRESH_TOKEN, value) }

    override var userId: Long
        get() = sharedPrefs.getLong(PREF_USER_ID, -1)
        set(value) = sharedPrefs.edit { putLong(PREF_USER_ID, value) }

    override fun isLoggedIn(userId: Long?): Boolean =
        if (userId != null) {
            userId == this.userId
        } else {
            accessToken != null
        }

    override fun setLoggedIn(session: SessionNetModel) {
        accessToken = session.accessToken
        refreshToken = session.refreshToken
        userId = session.userId.toLong()
    }

    override fun logout() {
        accessToken = null
        refreshToken = null
        userId = -1
    }

    companion object {
        const val PREF_FILE_NAME = "discusim.prefs"
        private const val PREF_ACCESS_TOKEN = "access_token"
        private const val PREF_REFRESH_TOKEN = "refresh_token"
        private const val PREF_USER_ID = "user_id"
    }
}
