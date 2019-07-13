package com.akhbulatov.discusim.data.moderator

import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import javax.inject.Inject

class ModeratorResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: ModeratorsResponse): PagedList<UserMiddle> {
        val users = map(response.moderators)
        return PagedList(response.cursor?.next, users)
    }

    fun map(models: List<ModeratorsResponse.ModeratorNetModel>): List<UserMiddle> =
        models.map { map(it) }

    fun map(model: ModeratorsResponse.ModeratorNetModel): UserMiddle =
        userResponseMapper.map(model.user)
}