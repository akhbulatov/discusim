package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.models.UserMiddleNetModel
import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.akhbulatov.discusim.data.global.network.models.UserPreviewNetModel
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import com.akhbulatov.discusim.domain.global.models.UserPreview
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    fun map(response: UserResponse): User = map(response.user)

    fun map(model: UserNetModel): User =
        model.let {
            User(
                it.id.toLong(),
                it.name,
                it.username,
                it.avatar.permalink,
                it.about,
                it.location,
                it.url,
                it.numLikesReceived,
                it.joinedAt
            )
        }

    fun map(model: UserMiddleNetModel): UserMiddle =
        model.let {
            UserMiddle(
                it.id.toLong(),
                it.name,
                it.username,
                it.avatar.permalink
            )
        }

    fun map(model: UserPreviewNetModel): UserPreview =
        model.let {
            UserPreview(
                it.id.toLong(),
                it.name,
                it.avatar.permalink
            )
        }
}