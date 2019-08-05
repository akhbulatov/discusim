package com.akhbulatov.discusim.data.global.local.prefs

import com.akhbulatov.discusim.data.global.network.models.SessionNetModel

interface PreferencesStorage {
    var accessToken: String?
    var refreshToken: String?
    var userId: Long

    fun isLoggedIn(userId: Long?): Boolean
    fun setLoggedIn(session: SessionNetModel)
    fun logout()
}
