package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.global.models.UserShort
import com.akhbulatov.discusim.presentation.ui.global.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.ProgressViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.getTintDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import kotlinx.android.synthetic.main.item_user_activity.*

class UserActivityAdapter(
    private val onUserClickListener: (UserShort) -> Unit,
    private val onItemClickListener: (Action) -> Unit
) : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

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
        holder.bind(getItem(position))
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

    inner class UserActivityViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        private lateinit var user: UserShort
        private lateinit var action: Action

        init {
            authorImageView.setOnClickListener { onUserClickListener(user) }
            itemView.setOnClickListener { onItemClickListener(action) }
        }

        override fun bind(item: Any) {
            if (item is Action) {
                action = item

                val context = itemView.context
                var authorAvatarUrl = ""
                var activity = ""
                var activityTypeDrawable: Drawable? = null

                if (item.discussionVote != null) {
                    // Discussion Vote
                    user = item.discussionVote.author

                    authorAvatarUrl = item.discussionVote.author.avatarUrl
                    val upvotedDiscussion = context.getString(
                        R.string.item_user_activity_upvoted_discussion,
                        item.discussionVote.discussion.title
                    )
                    activity = "${item.discussionVote.author.name} $upvotedDiscussion"
                    commentTextView.isVisible = false
                    activityTypeDrawable = context.getTintDrawable(
                        R.drawable.ic_favorite,
                        R.color.button_upvoted_background
                    )
                } else if (item.comment != null) {
                    // Comment
                    user = item.comment.author

                    authorAvatarUrl = item.comment.author.avatarUrl
                    val commented = context.getString(
                        R.string.item_user_activity_commented_discussion,
                        item.comment.discussion.title
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
                dateTextView.text = item.createdAt.getHumanCreatedTime(itemView.resources)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Action && newItem is Action) {
                    oldItem.equalsId(newItem)
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                oldItem == newItem
        }

        private const val ITEM_ACTION = 0
        private const val ITEM_PROGRESS = 1
    }
}