package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanTime
import com.akhbulatov.discusim.presentation.ui.global.utils.getTintDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user_activity.*

class UserActivityAdapter : ListAdapter<Action, UserActivityAdapter.UserActivityViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserActivityViewHolder {
        val itemView = parent.inflate(R.layout.item_user_activity)
        return UserActivityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserActivityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserActivityViewHolder(itemView: View) : BaseViewHolder<Action>(itemView) {
        override fun bind(item: Action) {
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
                activityTypeDrawable = context.getTintDrawable(R.drawable.ic_favorite, R.color.button_upvote)
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

            Glide.with(itemView)
                .load(authorAvatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(authorImageView)

            activityTextView.text = activity
            activityTypeImageView.setImageDrawable(activityTypeDrawable)
            dateTextView.text = item.createdAt.getHumanTime(itemView.resources)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Action>() {
            override fun areItemsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) { // Thread Vote
                    return oldItem.threadVote.id == newItem.threadVote.id
                } else if (oldItem.comment != null && newItem.comment != null) { // Comment
                    return oldItem.comment.id == oldItem.comment.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) { // Thread Vote
                    return oldItem.threadVote == newItem.threadVote
                } else if (oldItem.comment != null && newItem.comment != null) { // Comment
                    return oldItem.comment == oldItem.comment
                }
                return false
            }
        }
    }
}