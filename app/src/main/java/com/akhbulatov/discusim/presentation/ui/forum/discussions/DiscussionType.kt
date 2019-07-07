package com.akhbulatov.discusim.presentation.ui.forum.discussions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class DiscussionType : Parcelable {
    LATEST,
    HOT,
    POPULAR
}