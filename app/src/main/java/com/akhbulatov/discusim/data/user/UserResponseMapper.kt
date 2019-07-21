package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.global.network.models.user.UserNetModel
import com.akhbulatov.discusim.data.global.network.models.user.UserDetailsNetModel
import com.akhbulatov.discusim.data.global.network.models.user.UserShortNetModel
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.domain.global.models.user.UserShort
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    fun map(response: UserDetailsResponse): UserDetails = map(response.userDetails)

    fun map(models: List<UserNetModel>): List<User> = models.map { map(it) }

    fun map(model: UserDetailsNetModel): UserDetails =
        model.let {
            UserDetails(
                it.id.toLong(),
                it.name,
                it.username,
                it.avatar.permalink,
                it.profileUrl,
                it.isFollowing,
                it.isPrivate,
                it.about,
                it.location,
                it.url,
                it.numLikesReceived,
                it.joinedAt
            )
        }

    fun map(model: UserNetModel): User =
        model.let {
            User(
                it.id.toLong(),
                it.name,
                it.username,
                it.avatar.permalink
            )
        }

    fun map(model: UserShortNetModel): UserShort =
        model.let {
            UserShort(
                it.id.toLong(),
                it.name,
                it.avatar.permalink
            )
        }
}