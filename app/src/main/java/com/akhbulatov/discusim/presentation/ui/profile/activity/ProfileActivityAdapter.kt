package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanName
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_activity.*

class ProfileActivityAdapter : ListAdapter<Action, ProfileActivityAdapter.ActivityViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemView = parent.inflate(R.layout.item_profile_activity)
        return ActivityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ActivityViewHolder(itemView: View) : BaseViewHolder<Action>(itemView) {
        override fun bind(item: Action) {
            item.let {
                var authorUrl = ""
                var activity = ""
                var activityIconRes = 0

                if (it.threadVote != null) {
                    authorUrl = it.threadVote.author.avatarUrl
                    val vote = it.threadVote.voteType.getHumanName(itemView.resources)
                    activity = "${it.threadVote.author.name} $vote ${it.threadVote.thread.title}"
                    activityIconRes = R.drawable.ic_favorite
                } else if (it.post != null) {
                    authorUrl = it.post.author.avatarUrl
                    activity = "${it.post.author.name} commented ${it.post.message}" // TODO
                    activityIconRes = R.drawable.ic_comment
                }

                Glide.with(itemView)
                    .load(authorUrl)
                    .placeholder(R.drawable.img_user_placeholder)
                    .into(authorImageView)

                activityTextView.text = activity
                activityTypeImageView.setImageResource(activityIconRes)
                dateTextView.text = it.createdAt
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Action>() {
            override fun areItemsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) {
                    return oldItem.threadVote.id == newItem.threadVote.id
                } else if (oldItem.post != null && newItem.post != null) {
                    return oldItem.post.id == oldItem.post.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Action, newItem: Action): Boolean {
                if (oldItem.threadVote != null && newItem.threadVote != null) {
                    return oldItem.threadVote == newItem.threadVote
                } else if (oldItem.post != null && newItem.post != null) {
                    return oldItem.post == oldItem.post
                }
                return false
            }
        }
    }
}