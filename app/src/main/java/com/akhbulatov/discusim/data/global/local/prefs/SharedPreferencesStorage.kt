package com.akhbulatov.discusim.data.global.local.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.akhbulatov.discusim.domain.global.models.Session
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : PreferencesStorage {

    override var accessToken: String?
        get() = sharedPrefs.getString(PREF_ACCESS_TOKEN, null)
        set(value) = sharedPrefs.edit { putString(PREF_ACCESS_TOKEN, value) }

    override fun isLoggedIn(): Boolean = accessToken != null

    override fun setLoggedIn(session: Session) {
        accessToken = session.accessToken
    }

    override fun logout() {
        accessToken = null
    }

    companion object {
        private const val PREF_ACCESS_TOKEN = "access_token"
    }
}