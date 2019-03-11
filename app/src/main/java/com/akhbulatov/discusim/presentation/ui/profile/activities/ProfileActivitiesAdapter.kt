package com.akhbulatov.discusim.presentation.ui.profile.activities

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.presentation.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.global.utils.getHumanName
import com.akhbulatov.discusim.presentation.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_activity.*

class ProfileActivitiesAdapter : ListAdapter<Activity, ProfileActivitiesAdapter.ActivityViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemView = parent.inflate(R.layout.item_profile_activity)
        return ActivityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ActivityViewHolder(itemView: View) : BaseViewHolder<Activity>(itemView) {
        override fun bind(item: Activity) {
            item.let {
                var authorUrl = ""
                var activity = ""
                var activityIconRes = 0

                if (it.main != null) {
                    authorUrl = it.main.author.avatar.small.link
                    val vote = it.main.voteType.getHumanName(itemView.resources)
                    activity = "${it.main.author.name} $vote ${it.main.thread.title}"
                    activityIconRes = R.drawable.ic_favorite
                } else if (it.post != null) {
                    authorUrl = it.post.author.avatar.small.link
                    activity = "${it.post.author.name} commented ${it.post.message}" // TODO
                    activityIconRes = R.drawable.ic_comment
                }

                Glide.with(itemView)
                    .load(authorUrl)
                    .placeholder(R.drawable.img_user_placeholder)
                    .into(authorImageView)

                activityTextView.text = activity
                activityImageView.setImageResource(activityIconRes)
                createdAtTextView.text = it.createdAt
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Activity>() {
            override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                if (oldItem.main != null && newItem.main != null) {
                    return oldItem.main.id == newItem.main.id
                } else if (oldItem.post != null && newItem.post != null) {
                    return oldItem.post.id == oldItem.post.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                if (oldItem.main != null && newItem.main != null) {
                    return oldItem.main == newItem.main
                } else if (oldItem.post != null && newItem.post != null) {
                    return oldItem.post == oldItem.post
                }
                return false
            }
        }
    }
}