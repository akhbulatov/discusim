package com.akhbulatov.discusim.data.user

import com.akhbulatov.discusim.data.forums.ModeratorsResponse
import com.akhbulatov.discusim.data.global.network.models.UserNetModel
import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.global.models.UserDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserResponseMapper @Inject constructor() {
    fun map(model: UserNetModel): User =
        model.let {
            User(
                it.id,
                it.username,
                it.name,
                it.avatar.large.link,
                it.about,
                it.location,
                it.url,
                it.joinedAt
            )
        }

    fun map(response: UsersResponse): List<User> = response.users
    fun map(response: ModeratorsResponse): List<User> = response.moderators.map { it.user }

    fun map(response: UserDetailsResponse): UserDetails = response.userDetails

    fun map(response: UserPostsResponse): List<Post> = response.posts
}