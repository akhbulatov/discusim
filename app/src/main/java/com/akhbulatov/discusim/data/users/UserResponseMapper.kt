package com.akhbulatov.discusim.data.users

import com.akhbulatov.discusim.domain.global.models.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {
    fun map(response: UserResponse): User = response.user
}