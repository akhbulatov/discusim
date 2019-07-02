package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.akhbulatov.discusim.domain.global.models.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {
    fun map(model: UserNetModel): User =
        model.let {
            User(
                it.id,
                it.name,
                it.avatar.url
            )
        }
}