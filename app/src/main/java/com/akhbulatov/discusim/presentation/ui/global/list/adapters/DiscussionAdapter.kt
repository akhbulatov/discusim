package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.presentation.ui.global.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.ProgressViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadImage
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setDiscussionVote
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_discussion.*

class DiscussionAdapter(
    private val onItemClickListener: (Discussion) -> Unit
) : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_DISCUSSION -> {
                val itemView = parent.inflate(R.layout.item_discussion)
                DiscussionViewHolder(itemView)
            }
            else -> {
                val itemView = parent.inflate(R.layout.item_progress)
                ProgressViewHolder(itemView)
            }
        }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Discussion -> ITEM_DISCUSSION
            else -> ITEM_PROGRESS
        }

    fun showProgress(show: Boolean) {
        val progressVisible = isProgressVisible()

        if (show && !progressVisible) {
            val items = currentList.toMutableList()
            items.add(ProgressItem())
            submitList(items)
        } else if (!show && progressVisible) {
            val items = currentList.toMutableList()
            items.remove(items.last())
            submitList(items)
        }
    }

    private fun isProgressVisible(): Boolean {
        return currentList.isNotEmpty() && currentList.last() is ProgressItem
    }

    inner class DiscussionViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        private lateinit var discussion: Discussion

        init {
            itemView.setOnClickListener { onItemClickListener(discussion) }
        }

        override fun bind(item: Any) {
            if (item is Discussion) {
                discussion = item

                val context = itemView.context
                authorImageView.loadRoundedImage(context, item.author.avatarUrl)
                authorTextView.text = item.author.name
                dateTextView.text = item.createdAt.getHumanCreatedTime(itemView.resources)
                if (item.mediaList.isNotEmpty()) {
                    contentImageView.loadImage(context, item.mediaList.first().url)
                } else {
                    contentImageView.isVisible = false
                }
                if (item.topics.isNotEmpty()) {
                    item.topics.forEach {
                        val topicChip = Chip(context, null, R.attr.topicChipStyle).apply {
                            text = it.name
                        }
                        topicChipGroup.addView(topicChip)
                    }
                } else {
                    topicChipGroup.isVisible = false
                }
                titleTextView.text = item.title
                with(voteButton) {
                    text = item.upvotes.toString()
                    setDiscussionVote(item.isUpvoted)
                }
                commentsButton.text = item.comments.toString()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Discussion && newItem is Discussion) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                oldItem == newItem
        }

        private const val ITEM_DISCUSSION = 0
        private const val ITEM_PROGRESS = 1
    }
}