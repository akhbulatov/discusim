package com.akhbulatov.discusim.presentation.ui.followitems

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_follow.*

class FollowItemsAdapter : ListAdapter<Any, FollowItemsAdapter.FollowItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowItemViewHolder {
        val itemView = parent.inflate(R.layout.item_follow)
        return FollowItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FollowItemViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun bind(item: Any) {
            var avatarUrl = ""
            var name = ""

            if (item is User) {
                avatarUrl = item.avatar.small.link
                name = item.name
            } else if (item is Forum) {
                avatarUrl = item.favicon.link
                name = item.name
            }

            Glide.with(itemView)
                .load(avatarUrl)
                .placeholder(R.drawable.img_user_placeholder) // TODO
                .into(avatarImageView)

            nameTextView.text = name
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                if (oldItem is User && newItem is User) {
                    return oldItem.id == newItem.id
                } else if (oldItem is Forum && newItem is Forum) {
                    return oldItem.id == newItem.id
                }
                return false
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                if (oldItem is User && newItem is User) {
                    return oldItem == newItem
                } else if (oldItem is Forum && newItem is Forum) {
                    return oldItem == newItem
                }
                return false
            }
        }
    }
}