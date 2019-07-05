package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.akhbulatov.discusim.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import org.jetbrains.anko.dip

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable {
    val drawable = this.getDrawable(drawableRes)!!
    drawable.setTint(this.color(colorRes))
    return drawable
}

fun TextView.setTintStartDrawable(@ColorRes colorRes: Int) {
    compoundDrawablesRelative.first()?.setTint(context.color(colorRes))
}

fun TextView.showTextIfNotEmpty(text: String?) {
    this.text = text
    isVisible = !text.isNullOrBlank()
}

fun ImageView.loadImage(ctx: Context? = null, url: String) {
    Glide.with(ctx ?: context)
        .load(url)
        .into(this)
}

fun ImageView.loadRoundedImage(ctx: Context? = null, url: String) {
    Glide.with(ctx ?: context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun MaterialButton.setFollow(isFollowing: Boolean) {
    val iconDrawable = if (isFollowing) context.getDrawable(R.drawable.ic_done) else null
    val backgroundColor = if (isFollowing) R.color.button_following else R.color.button_follow
    val textResId = if (isFollowing) R.string.msg_following else R.string.msg_follow

    icon = iconDrawable
    iconTint = ColorStateList.valueOf(context.color(R.color.primary))
    setBackgroundColor(context.color(backgroundColor))
    setText(textResId)
}

fun MaterialButton.setThreadVote(isUpvoted: Boolean) {
    val backgroundColor = if (isUpvoted) R.color.button_upvoted_background else R.color.button_thread_background
    val iconTintColor = if (isUpvoted) R.color.button_thread_upvoted_icon else R.color.button_thread_icon
    val textColor = if (isUpvoted) R.color.button_thread_upvoted_text else R.color.button_thread_text
    val stroke = if (isUpvoted) 0 else dip(1)

    setBackgroundColor(context.color(backgroundColor))
    iconTint = ColorStateList.valueOf(context.color(iconTintColor))
    setTextColor(context.color(textColor))
    strokeWidth = stroke
}