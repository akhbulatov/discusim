package com.akhbulatov.discusim.domain.session

import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionInteractor @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    fun getAuthorizeUrl(): String = sessionRepository.getAuthorizeUrl()

    /**
     * Извлекает код, необходимый для выполнения входа (получения токена).
     *
     * @param url Редирект URL с кодом для входа.
     * @return Код для входа или `null`, если код не найден.
     */
    fun fetchAuthCode(url: String): String? {
        if (url.contains("code")) {
            return url.substringAfterLast("=")
        }
        return null
    }

    fun isLoggedIn(): Boolean = sessionRepository.isLoggedIn()

    fun login(code: String): Completable =
        sessionRepository.login(code)
            .doOnSuccess { sessionRepository.setLoggedIn(it) }
            .ignoreElement()

    fun logout() = sessionRepository.logout()
}