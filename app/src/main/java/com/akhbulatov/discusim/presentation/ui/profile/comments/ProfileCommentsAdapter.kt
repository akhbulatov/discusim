package com.akhbulatov.discusim.presentation.ui.profile.comments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.presentation.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_profile_comment.*

class ProfileCommentsAdapter : ListAdapter<Comment, ProfileCommentsAdapter.CommentViewHolder>(COMMENT_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = parent.inflate(R.layout.item_profile_comment)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CommentViewHolder(itemView: View) : BaseViewHolder<Comment>(itemView) {
        override fun bind(item: Comment) {
            item.let {
                Glide.with(itemView)
                    .load(it.author.avatar.small.link)
                    .placeholder(R.drawable.img_user_placeholder)
                    .into(authorImageView)

                authorTextView.text = it.author.name
                createdAtTextView.text = it.createdAt
                messageTextView.text = it.message
                upvotesTextView.text = it.likes.toString()
            }
        }
    }

    companion object {
        val COMMENT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}