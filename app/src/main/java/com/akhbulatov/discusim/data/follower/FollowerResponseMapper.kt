package com.akhbulatov.discusim.data.follower

import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.user.User
import javax.inject.Inject

class FollowerResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper
) {

    fun map(response: FollowersResponse): PagedList<User> {
        val followers = userResponseMapper.map(response.followers)
        return PagedList(response.cursor?.next, followers)
    }
}
