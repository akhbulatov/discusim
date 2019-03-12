package com.akhbulatov.discusim.presentation.ui.users

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class UserType : Parcelable {
    FOLLOWING,
    FOLLOWER,
    TOP_COMMENTER
}