package com.akhbulatov.discusim.presentation.ui.threads

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanTime
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setVote
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_thread.*

class ThreadsAdapter : ListAdapter<Thread, ThreadsAdapter.ThreadViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadViewHolder {
        val itemView = parent.inflate(R.layout.item_thread)
        return ThreadViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ThreadViewHolder(itemView: View) : BaseViewHolder<Thread>(itemView) {
        override fun bind(item: Thread) {
            authorImageView.loadRoundedImage(itemView.context, item.author.avatarUrl)
            authorTextView.text = item.author.name
            dateTextView.text = item.createdAt.getHumanTime(itemView.resources)

            if (item.mediaList != null) {
                Glide.with(itemView)
                    .load(item.mediaList.first().url)
                    .into(contentImageView)
            } else {
                contentImageView.isVisible = false
            }

            topicsChipGroup.isVisible = false // TODO
            titleTextView.text = item.title
            with(voteButton) {
                text = item.upvotes.toString()
                setVote(item.upvoted)
            }
            commentsButton.text = item.comments.toString()
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Thread>() {
            override fun areItemsTheSame(oldItem: Thread, newItem: Thread): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Thread, newItem: Thread): Boolean =
                oldItem == newItem
        }
    }
}