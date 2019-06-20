package com.akhbulatov.discusim.data.session

import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import com.akhbulatov.discusim.domain.global.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionResponseMapper @Inject constructor() {
    fun map(model: SessionNetModel): Session =
        model.let {
            Session(
                it.userId,
                it.accessToken,
                it.refreshToken
            )
        }
}