package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.akhbulatov.discusim.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable {
    val drawable = this.getDrawable(drawableRes)!!
    drawable.setTint(this.color(colorRes))
    return drawable
}

fun ImageView.loadRoundedImage(ctx: Context? = null, url: String) {
    Glide.with(ctx ?: context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun MaterialButton.setVote(upvoted: Boolean) {
    val backgroundColor = if (upvoted) R.color.button_upvote_background else R.color.button_thread_background
    val iconTintColor = if (upvoted) R.color.button_thread_upvote_icon else R.color.button_thread_icon
    val textColor = if (upvoted) R.color.button_thread_upvote_text else R.color.button_thread_text

    setBackgroundColor(context.color(backgroundColor))
    iconTint = ColorStateList.valueOf(context.color(iconTintColor))
    setTextColor(context.color(textColor))
    if (upvoted) strokeWidth = 0 // Убирает рамки в случае лайка
}