package com.akhbulatov.discusim.data.global.local.prefs

import com.akhbulatov.discusim.domain.global.models.Auth

interface PreferencesStorage {
    var userId: Long
    var accessToken: String?
    var refreshToken: String?

    fun isLoggedIn(): Boolean
    fun setLoggedIn(auth: Auth)
    fun logout()
}