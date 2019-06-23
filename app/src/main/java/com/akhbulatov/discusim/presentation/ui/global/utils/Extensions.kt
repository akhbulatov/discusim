package com.akhbulatov.discusim.presentation.ui.global.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

@Suppress("unused")
fun Fragment.showSnackbar(@StringRes resId: Int) {
    Snackbar.make(this.view!!, resId, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(text: String) {
    Snackbar.make(this.view!!, text, Snackbar.LENGTH_SHORT).show()
}