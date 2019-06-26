package com.akhbulatov.discusim.presentation.ui.profile.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.views.list.BaseViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_post.*

class ProfilePostsAdapter : ListAdapter<Comment, ProfilePostsAdapter.PostViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = parent.inflate(R.layout.item_profile_post)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostViewHolder(itemView: View) : BaseViewHolder<Comment>(itemView) {
        override fun bind(item: Comment) {
            item.let {
                Glide.with(itemView)
                    .load(it.author.avatarUrl)
                    .placeholder(R.drawable.img_user_placeholder)
                    .into(authorImageView)

                authorTextView.text = it.author.name
//                createdAtTextView.text = it.createdAt
                messageTextView.text = it.message
//                upvotesTextView.text = it.likes.toString()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}