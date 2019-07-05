package com.akhbulatov.discusim.data.following

import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.UserMiddle
import javax.inject.Inject

class FollowingResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: FollowingResponse): PagedList<UserMiddle> {
        val followers = response.following.map { userResponseMapper.map(it) }
        return PagedList(response.cursor?.next, followers)
    }
}