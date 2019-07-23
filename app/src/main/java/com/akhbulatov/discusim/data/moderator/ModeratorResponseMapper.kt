package com.akhbulatov.discusim.data.moderator

import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import javax.inject.Inject

class ModeratorResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: ModeratorsResponse): PagedList<User> {
        val users = map(response.moderators)
        return PagedList(response.cursor?.next, users)
    }

    fun map(models: List<ModeratorsResponse.ModeratorNetModel>): List<User> =
        models.map { map(it) }

    fun map(model: ModeratorsResponse.ModeratorNetModel): User =
        userResponseMapper.map(model.user)
}
