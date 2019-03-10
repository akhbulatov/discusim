package com.akhbulatov.discusim.presentation.ui.profile.followers

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_following.*

class ProfileFollowersAdapter : ListAdapter<User, ProfileFollowersAdapter.FollowerViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val itemView = parent.inflate(R.layout.item_profile_follower)
        return FollowerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FollowerViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {
        override fun bind(item: User) {
            item.let {
                Glide.with(itemView)
                    .load(it.avatar.small.link)
                    .placeholder(R.drawable.img_user_placeholder)
                    .into(avatarImageView)

                nameTextView.text = it.name
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}