package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Auth
import io.reactivex.Single

interface SessionRepository {
    fun getAuthorizeUrl(): String

    fun isLoggedIn(userId: Long? = null): Boolean
    fun setLoggedIn(auth: Auth)

    fun login(code: String): Single<Auth>
    fun logout()
}