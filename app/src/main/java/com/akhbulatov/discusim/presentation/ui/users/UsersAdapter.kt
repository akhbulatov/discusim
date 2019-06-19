package com.akhbulatov.discusim.presentation.ui.users

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.ui.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.*

class UsersAdapter : ListAdapter<User, UsersAdapter.UserViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = parent.inflate(R.layout.item_user)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {
        override fun bind(item: User) {
            item.let {
                Glide.with(itemView)
                    .load(it.avatar.small.link)
                    .placeholder(R.drawable.img_user_placeholder) // TODO
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