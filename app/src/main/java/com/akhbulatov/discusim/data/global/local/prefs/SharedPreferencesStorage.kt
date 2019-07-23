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
        userId = session.userId.toLong()
    }

    override fun logout() {
        accessToken = null
        userId = -1
    }

    companion object {
        private const val PREF_ACCESS_TOKEN = "access_token"
        private const val PREF_USER_ID = "user_id"
    }
}
