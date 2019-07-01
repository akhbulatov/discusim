package com.akhbulatov.discusim.data.global.local.prefs

import com.akhbulatov.discusim.domain.global.models.Session

interface PreferencesStorage {
    var userId: Long
    var accessToken: String?

    fun isLoggedIn(): Boolean
    fun setLoggedIn(session: Session)
    fun logout()
}