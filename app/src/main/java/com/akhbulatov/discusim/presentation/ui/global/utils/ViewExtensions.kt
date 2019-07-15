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
import com.akhbulatov.discusim.domain.global.models.Vote
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.razir.progressbutton.DrawableButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import org.jetbrains.anko.dip

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getTintDrawable(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable {
    val mutableDrawable = getDrawable(drawableRes)!!.mutate()
    mutableDrawable.setTint(this.color(colorRes))
    return mutableDrawable
}

fun TextView.setTintStartDrawable(@ColorRes colorRes: Int) {
    val mutableDrawable = compoundDrawablesRelative.first()!!.mutate()
    mutableDrawable.setTint(context.color(colorRes))
}

fun TextView.setTintEndDrawable(@ColorRes colorRes: Int) {
    val mutableDrawable = compoundDrawablesRelative[2]!!.mutate()
    mutableDrawable.setTint(context.color(colorRes))
}

fun TextView.showTextIfNotEmpty(text: String?) {
    this.text = text
    isVisible = !text.isNullOrBlank()
}

fun ImageView.loadImage(
    ctx: Context? = null,
    url: String,
    @DrawableRes placeholderResId: Int? = null
) {
    val imageResId = placeholderResId ?: R.drawable.img_placeholder

    Glide.with(ctx ?: context)
        .load(url)
        .placeholder(imageResId)
        .into(this)
}

/**
 * @param placeholderResId По умолчанию используется placeholder юзера.
 */
fun ImageView.loadRoundedImage(
    ctx: Context? = null,
    url: String,
    @DrawableRes placeholderResId: Int? = null
) {
    val imageResId = placeholderResId ?: R.drawable.img_user_placeholder

    Glide.with(ctx ?: context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(imageResId)
        .into(this)
}

fun MaterialButton.setFollow(following: Boolean) {
    val backgroundColor = if (following) R.color.button_following else R.color.button_follow
    val iconDrawable = if (following) context.getDrawable(R.drawable.ic_done) else null
    val textResId = if (following) R.string.msg_following else R.string.msg_follow

    setBackgroundColor(context.color(backgroundColor))
    icon = iconDrawable
    iconTint = ColorStateList.valueOf(context.color(R.color.primary))
    setText(textResId)

    isSelected = following
}

/**
 * Размер прогресса должен совпадать с размером иконки кнопки - 18dp.
 * (6.5 + 2.5 (stroke progress)) * 2 = 18
 */
private const val BUTTON_PROGRESS_PX = 6.5f

fun MaterialButton.showFollowProgress(show: Boolean) {
    if (show) {
        showProgress {
            gravity = DrawableButton.GRAVITY_CENTER
            progressRadiusPx = dip(BUTTON_PROGRESS_PX)
            progressColorRes = R.color.primary
        }
        icon = null
    } else {
        val textResId = if (isSelected) R.string.msg_following else R.string.msg_follow
        hideProgress(textResId)
    }
    isClickable = !show
}

fun MaterialButton.resetFollowBeforeProgress() {
    val iconDrawable = if (isSelected) context.getDrawable(R.drawable.ic_done) else null
    val textResId = if (isSelected) R.string.msg_following else R.string.msg_follow

    icon = iconDrawable
    iconTint = ColorStateList.valueOf(context.color(R.color.primary))
    setText(textResId)
}

fun MaterialButton.setDiscussionVote(vote: Vote) {
    val backgroundColor = when (vote.type) {
        Vote.Type.NO_VOTE,
        Vote.Type.DOWNVOTE -> R.color.button_discussion_background
        Vote.Type.UPVOTE -> R.color.button_upvoted_background
    }
    val iconColor = when (vote.type) {
        Vote.Type.NO_VOTE,
        Vote.Type.DOWNVOTE -> R.color.button_discussion_icon
        Vote.Type.UPVOTE -> R.color.button_discussion_upvoted_icon
    }
    val textColor = when (vote.type) {
        Vote.Type.NO_VOTE,
        Vote.Type.DOWNVOTE -> R.color.button_discussion_text
        Vote.Type.UPVOTE -> R.color.button_discussion_upvoted_text
    }
    val stroke = when (vote.type) {
        Vote.Type.NO_VOTE,
        Vote.Type.DOWNVOTE -> dip(1)
        Vote.Type.UPVOTE -> 0
    }

    setBackgroundColor(context.color(backgroundColor))
    icon = context.getDrawable(R.drawable.ic_favorite)
    iconTint = ColorStateList.valueOf(context.color(iconColor))
    strokeWidth = stroke
    setTextColor(context.color(textColor))
    text = vote.upvotes.toString()

    isSelected = vote.type == Vote.Type.UPVOTE
}

fun MaterialButton.showDiscussionVoteProgress(show: Boolean, vote: Vote) {
    if (show) {
        val progressColor = if (isSelected) R.color.primary else R.color.accent

        showProgress {
            gravity = DrawableButton.GRAVITY_CENTER
            progressRadiusPx = dip(BUTTON_PROGRESS_PX)
            progressColorRes = progressColor
        }
        icon = null
    } else {
        hideProgress(vote.upvotes.toString())
    }
    isClickable = !show
}

fun MaterialButton.resetDiscussionVoteBeforeProgress(vote: Vote) {
    val iconColor = if (isSelected) R.color.button_discussion_upvoted_icon else R.color.button_discussion_icon
    icon = context.getDrawable(R.drawable.ic_favorite)
    iconTint = ColorStateList.valueOf(context.color(iconColor))
    text = vote.upvotes.toString()
}