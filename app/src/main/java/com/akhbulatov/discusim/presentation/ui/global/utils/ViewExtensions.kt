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
import com.akhbulatov.discusim.domain.global.models.VoteType
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

fun TextView.updateVotesText(voteType: VoteType) {
    var oldVotes = text.toString().trim().toInt()
    val newVotes = when (voteType) {
        VoteType.NO_VOTE,
        VoteType.DOWNVOTE -> --oldVotes
        VoteType.UPVOTE -> ++oldVotes
    }
    text = newVotes.toString()
}

fun MaterialButton.setDiscussionVote(voteType: VoteType) {
    val backgroundColor = when (voteType) {
        VoteType.NO_VOTE -> R.color.button_discussion_background
        VoteType.UPVOTE -> R.color.button_upvoted_background
        VoteType.DOWNVOTE -> throw IllegalArgumentException() // TODO
    }
    val iconColor = when (voteType) {
        VoteType.NO_VOTE -> R.color.button_discussion_icon
        VoteType.UPVOTE -> R.color.button_discussion_upvoted_icon
        VoteType.DOWNVOTE -> throw IllegalArgumentException() // TODO
    }
    val textColor = when (voteType) {
        VoteType.NO_VOTE -> R.color.button_discussion_text
        VoteType.UPVOTE -> R.color.button_discussion_upvoted_text
        VoteType.DOWNVOTE -> throw IllegalArgumentException() // TODO
    }
    val stroke = when (voteType) {
        VoteType.NO_VOTE -> dip(1)
        VoteType.UPVOTE -> 0
        VoteType.DOWNVOTE -> throw IllegalArgumentException() // TODO
    }

    setBackgroundColor(context.color(backgroundColor))
    icon = context.getDrawable(R.drawable.ic_favorite)
    iconTint = ColorStateList.valueOf(context.color(iconColor))
    setTextColor(context.color(textColor))
    strokeWidth = stroke

    isSelected = voteType == VoteType.UPVOTE
}

fun MaterialButton.showDiscussionVoteProgress(show: Boolean) {
    if (show) {
        // Размер прогресса должен совпадать с размером иконки кнопки - 18dp.
        // (6.5 + 2.5 (stroke progress)) * 2 = 18
        val progressPx = 6.5f
        val progressColor = if (isSelected) R.color.primary else R.color.accent

        showProgress {
            buttonText = text.toString()
            gravity = DrawableButton.GRAVITY_TEXT_START
            progressRadiusPx = dip(progressPx)
            progressColorRes = progressColor
        }
        icon = null
    } else {
        hideProgress(text.toString())
    }
}

fun MaterialButton.resetDiscussionVoteBeforeProgress() {
    val iconColor = if (isSelected) R.color.button_discussion_upvoted_icon else R.color.button_discussion_icon
    icon = context.getDrawable(R.drawable.ic_favorite)
    iconTint = ColorStateList.valueOf(context.color(iconColor))
    text = text.trim()
}