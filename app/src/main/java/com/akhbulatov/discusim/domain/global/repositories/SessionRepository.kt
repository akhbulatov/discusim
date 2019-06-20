package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Session
import io.reactivex.Single

interface SessionRepository {
    fun getAuthorizeUrl(): String

    fun isLoggedIn(): Boolean
    fun setLoggedIn(session: Session)

    fun getUserId(): Long

    fun login(code: String): Single<Session>
    fun logout()
}