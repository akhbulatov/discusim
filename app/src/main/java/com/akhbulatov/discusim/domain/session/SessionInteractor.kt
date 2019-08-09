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

    fun fetchAuthCode(url: String): String? = sessionRepository.fetchAuthCode(url)

    fun isLoggedIn(userId: Long? = null): Boolean =
        sessionRepository.isLoggedIn(userId)

    fun login(code: String): Completable =
        sessionRepository.login(code)

    fun logout() = sessionRepository.logout()
}
