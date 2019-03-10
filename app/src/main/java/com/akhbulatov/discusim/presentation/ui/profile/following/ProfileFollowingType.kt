package com.akhbulatov.discusim.presentation.ui.profile.following

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ProfileFollowingType : Parcelable {
    USER,
    FORUM
}