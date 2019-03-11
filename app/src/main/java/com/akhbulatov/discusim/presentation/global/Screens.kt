package com.akhbulatov.discusim.presentation.global

import android.os.Parcelable
import com.akhbulatov.discusim.presentation.ui.profile.following.ProfileFollowingType
import kotlinx.android.parcel.Parcelize
import me.aartikov.alligator.Screen

object Screens {
    object AuthFlow : Screen
    object Auth : Screen

    object MainFlow : Screen

    @Parcelize
    data class Profile(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class ProfileActivities(val userId: Long): Screen, Parcelable

    @Parcelize
    data class ProfileComments(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class ProfileFollowers(val userId: Long) : Screen, Parcelable

    @Parcelize
    data class ProfileFollowing(val userId: Long, val type: ProfileFollowingType) : Screen, Parcelable
}