package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.forum.ModeratorsResponse
import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
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

    fun map(response: UsersResponse): List<User> = response.users
    fun map(response: ModeratorsResponse): List<User> = response.moderators.map { it.user }

    fun map(response: UserDetailsResponse): UserDetails = response.userDetails

    fun map(response: UserPostsResponse): List<Comment> = response.posts
}