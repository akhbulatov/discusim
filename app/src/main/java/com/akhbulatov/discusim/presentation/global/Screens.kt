package com.akhbulatov.discusim.presentation.global

import android.os.Parcelable
import com.akhbulatov.discusim.presentation.ui.users.UserType
import kotlinx.android.parcel.Parcelize
import me.aartikov.alligator.Screen

object Screens {
    object AuthFlow : Screen
    object Auth : Screen

    object MainFlow : Screen

    @Parcelize
    data class Profile(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class ProfileActivities(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class ProfilePosts(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class Users(val itemId: String, val type: UserType) : Screen, Parcelable

    @Parcelize
    data class Forum(val forumId: String) : Screen, Parcelable

    @Parcelize
    data class Forums(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class Channel(val forumId: String) : Screen, Parcelable

    @Parcelize
    data class Threads(val forumId: String) : Screen, Parcelable
}