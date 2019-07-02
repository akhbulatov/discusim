package com.akhbulatov.discusim.data.session

import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import com.akhbulatov.discusim.domain.global.models.Session
import javax.inject.Inject

class SessionResponseMapper @Inject constructor() {
    fun map(model: SessionNetModel): Session = Session(model.accessToken)
}