package com.akhbulatov.discusim.domain.global.repositories

import io.reactivex.Completable

interface SessionRepository {
    fun getAuthorizeUrl(): String

    /**
     * Извлекает код, необходимый для выполнения входа (получения токена).
     *
     * @param url Редирект URL с кодом для входа.
     * @return Код для входа или `null`, если код не найден.
     */
    fun fetchAuthCode(url: String): String?

    fun isLoggedIn(userId: Long?): Boolean

    fun login(code: String): Completable
    fun logout()
}
