package com.akhbulatov.discusim.presentation.global

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import me.aartikov.alligator.Screen

object Screens {
    object AuthFlow : Screen
    object Auth : Screen

    object MainFlow : Screen

    @Parcelize
    data class Profile(val userId: Long) : Screen, Parcelable
}