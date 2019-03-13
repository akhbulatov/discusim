package com.akhbulatov.discusim.presentation.ui.forums

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.global.utils.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_forum.*

class ForumsAdapter : ListAdapter<Forum, ForumsAdapter.ForumViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val itemView = parent.inflate(R.layout.item_forum)
        return ForumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForumViewHolder(itemView: View) : BaseViewHolder<Forum>(itemView) {
        override fun bind(item: Forum) {
            item.let {
                Glide.with(itemView)
                    .load(it.faviconUrl)
                    .placeholder(R.drawable.img_user_placeholder) // TODO
                    .into(avatarImageView)

                nameTextView.text = it.name
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Forum>() {
            override fun areItemsTheSame(oldItem: Forum, newItem: Forum): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Forum, newItem: Forum): Boolean =
                oldItem == newItem
        }
    }
}