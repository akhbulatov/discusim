package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanTime
import com.akhbulatov.discusim.presentation.ui.global.utils.getTintDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.views.list.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.views.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.views.list.ProgressViewHolder
import kotlinx.android.synthetic.main.item_user_activity.*

class UserActivityAdapter : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_ACTION -> {
                val itemView = parent.inflate(R.layout.item_user_activity)
                UserActivityViewHolder(itemView)
            }
            else -> {
                val itemView = parent.inflate(R.layout.item_progress)
                ProgressViewHolder(itemView)
            }
        }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        if (holder is UserActivityViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Action -> ITEM_ACTION
            else -> ITEM_PROGRESS
        }

    fun showProgress(show: Boolean) {
        val progressVisible = isProgressVisible()

        if (show && !progressVisible) {
            val items = currentList.toMutableList()
            items.add(ProgressItem())
            submitList(items)
        } else if (!show && progressVisible) {
            val items = currentList.toMutableList()
            items.remove(items.last())
            submitList(items)
        }
    }

    private fun isProgressVisible(): Boolean {
        return currentList.isNotEmpty() && currentList.last() is ProgressItem
    }

    class UserActivityViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun bind(item: Any) {
            if (item is Action) {
                val context = itemView.context
                var authorAvatarUrl = ""
                var activity = ""
                var activityTypeDrawable: Drawable? = null

                if (item.threadVote != null) {
                    // Thread Vote
                    authorAvatarUrl = item.threadVote.author.avatarUrl
                    val upvotedThread = context.getString(
                        R.string.item_user_activity_upvoted_thread,
                        item.threadVote.thread.title
                    )
                    activity = "${item.threadVote.author.name} $upvotedThread"
                    commentTextView.isVisible = false
                    activityTypeDrawable = context.getTintDrawable(
                        R.drawable.ic_favorite,
                        R.color.button_upvote_background
                    )
                } else if (item.comment != null) {
                    // Comment
                    authorAvatarUrl = item.comment.author.avatarUrl
                    val commented = context.getString(
                        R.string.item_user_activity_commented_thread,
                        item.comment.thread.title
                    )
                    activity = "${item.comment.author.name} $commented"
                    with(commentTextView) {
                        text = item.comment.message
                        isVisible = true
                    }
                    activityTypeDrawable = context.getTintDrawable(R.drawable.ic_comment, R.color.accent)
                }

                authorImageView.loadRoundedImage(context, authorAvatarUrl)
                activityTextView.text = activity
                activityTypeImageView.setImageDrawable(activityTypeDrawable)
                dateTextView.text = item.createdAt.getHumanTime(itemView.resources)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Action && newItem is Action) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Action && newItem is Action) {
                    oldItem == newItem
                } else {
                    true
                }
        }

        private const val ITEM_ACTION = 0
        private const val ITEM_PROGRESS = 1
    }
}