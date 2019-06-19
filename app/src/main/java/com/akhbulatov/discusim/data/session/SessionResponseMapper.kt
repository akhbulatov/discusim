package com.akhbulatov.discusim.data.session

import com.akhbulatov.discusim.data.global.network.models.AuthNetModel
import com.akhbulatov.discusim.domain.global.models.Auth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionResponseMapper @Inject constructor() {
    fun map(auth: AuthNetModel): Auth =
        auth.let {
            Auth(
                it.userId,
                it.accessToken,
                it.refreshToken
            )
        }
}