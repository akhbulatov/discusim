package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import javax.inject.Inject

class UsersResponseMapper @Inject constructor() {
    fun map(response: UsersResponse): List<User> = response.users
    fun map(response: UserDetailsResponse): UserDetails = response.userDetails

    fun map(response: UserCommentsResponse): List<Comment> = response.comments
    fun map(response: UserForumsResponse): List<Forum> = response.forums
}