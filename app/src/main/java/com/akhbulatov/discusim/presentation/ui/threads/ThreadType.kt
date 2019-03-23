package com.akhbulatov.discusim.presentation.ui.threads

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ThreadType : Parcelable {
    LATEST,
    HOT,
    POPULAR
}