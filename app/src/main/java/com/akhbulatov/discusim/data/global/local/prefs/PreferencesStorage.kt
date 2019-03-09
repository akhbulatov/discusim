package com.akhbulatov.discusim.data.global.local.prefs

import android.content.Context
import androidx.core.content.edit
import com.akhbulatov.discusim.domain.global.models.Auth
import javax.inject.Inject

interface PreferencesStorage {
    var userId: Long
    var accessToken: String?
    var refreshToken: String?

    fun isLoggedIn(): Boolean
    fun setLoggedIn(auth: Auth)
    fun logout()
}

class SharedPreferencesStorage @Inject constructor(context: Context) : PreferencesStorage {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var userId: Long
        get() = prefs.getLong(PREF_USER_ID, -1)
        set(value) = prefs.edit { putLong(PREF_USER_ID, value) }

    override var accessToken: String?
        get() = prefs.getString(PREF_ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(PREF_ACCESS_TOKEN, value) }

    override var refreshToken: String?
        get() = prefs.getString(PREF_REFRESH_TOKEN, null)
        set(value) = prefs.edit { putString(PREF_REFRESH_TOKEN, value) }

    override fun isLoggedIn(): Boolean {
        return userId != -1L && accessToken != null && refreshToken != null
    }

    override fun setLoggedIn(auth: Auth) {
        userId = auth.userId
        accessToken = auth.accessToken
        refreshToken = auth.refreshToken
    }

    override fun logout() {
        userId = -1
        accessToken = null
        refreshToken = null
    }

    companion object {
        const val PREFS_NAME = "discusim.prefs"

        const val PREF_USER_ID = "user_id"
        const val PREF_ACCESS_TOKEN = "access_token"
        const val PREF_REFRESH_TOKEN = "refresh_token"
    }
}