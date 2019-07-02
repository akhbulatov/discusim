package com.akhbulatov.discusim.data.session

import com.akhbulatov.discusim.data.global.local.prefs.PreferencesStorage
import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Session
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import io.reactivex.Single
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val prefsStorage: PreferencesStorage,
    private val oAuthParams: OAuthParams,
    private val sessionResponseMapper: SessionResponseMapper,
    private val schedulers: SchedulersProvider
) : SessionRepository {

    override fun getAuthorizeUrl(): String {
        return "${oAuthParams.authorizeUrl}?" +
                "client_id=${oAuthParams.clientId}&" +
                "scope=${oAuthParams.scope}&" +
                "response_type=code&" +
                "redirect_uri=${oAuthParams.redirectUri}"
    }

    override fun isLoggedIn(): Boolean = prefsStorage.isLoggedIn()

    override fun setLoggedIn(session: Session) {
        prefsStorage.setLoggedIn(session)
    }

    override fun login(code: String): Single<Session> =
        api.login(
            "authorization_code",
            oAuthParams.clientId,
            oAuthParams.clientSecret,
            oAuthParams.redirectUri,
            code
        )
            .map { sessionResponseMapper.map(it) }
            .subscribeOn(schedulers.io())

    override fun logout() {
        prefsStorage.logout()
    }
}