package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.UserDetails
import javax.inject.Inject

class UsersResponseMapper @Inject constructor() {
    fun map(response: UserDetailsResponse): UserDetails = response.userDetails
    fun map(response: UserCommentsResponse): List<Comment> = response.comments
}