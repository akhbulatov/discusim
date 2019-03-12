package com.akhbulatov.discusim.presentation.ui.followitems

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class FollowItemType : Parcelable {
    FOLLOWING_USER,
    FOLLOWING_FORUM,
    FOLLOWER,
    TOP_COMMENTERS
}