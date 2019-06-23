package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanName
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanTime
import com.akhbulatov.discusim.presentation.ui.global.utils.getTintDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.bumptech.glide.Glide
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
            var activityDrawable: Drawable? = null

            if (item.threadVote != null) {
                // ThreadVote
                authorAvatarUrl = item.threadVote.author.avatarUrl
                val vote = item.threadVote.voteType.getHumanName(itemView.resources)
                activity = "${item.threadVote.author.name} $vote ${item.threadVote.thread.title}"
                activityDrawable = context.getTintDrawable(R.drawable.ic_favorite, R.color.button_upvote)
            } else if (item.comment != null) {
                // Comment
                authorAvatarUrl = item.comment.author.avatarUrl
                val comment = itemView.context.getString(R.string.item_user_activity_comment)
                activity = "${item.comment.author.name} $comment ${item.comment.message}"
                activityDrawable = context.getTintDrawable(R.drawable.ic_comment, R.color.accent)
            }

            Glide.with(itemView)
                .load(authorAvatarUrl)
                .placeholder(R.drawable.img_user_placeholder)
                .into(authorImageView)

            activityTextView.text = activity
            activityImageView.setImageDrawable(activityDrawable)
            dateTextView.text = item.createdAt.getHumanTime(itemView.resources)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Action>() {
            override fun areItemsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) { // ThreadVote
                    return oldItem.threadVote.id == newItem.threadVote.id
                } else if (oldItem.comment != null && newItem.comment != null) { // Comment
                    return oldItem.comment.id == oldItem.comment.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) { // ThreadVote
                    return oldItem.threadVote == newItem.threadVote
                } else if (oldItem.comment != null && newItem.comment != null) { // Comment
                    return oldItem.comment == oldItem.comment
                }
                return false
            }
        }
    }
}