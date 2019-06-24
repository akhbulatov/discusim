package com.akhbulatov.discusim.presentation.ui.global.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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