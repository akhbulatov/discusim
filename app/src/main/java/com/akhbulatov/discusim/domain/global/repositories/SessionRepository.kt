package com.akhbulatov.discusim.domain.global.repositories

import io.reactivex.Completable

interface SessionRepository {
    fun getAuthorizeUrl(): String

    fun isLoggedIn(userId: Long?): Boolean

    fun login(code: String): Completable
    fun logout()
}