package com.akhbulatov.discusim.data.following

import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import javax.inject.Inject

class FollowingResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: FollowingResponse): PagedList<User> {
        val followers = userResponseMapper.map(response.following)
        return PagedList(response.cursor?.next, followers)
    }
}
