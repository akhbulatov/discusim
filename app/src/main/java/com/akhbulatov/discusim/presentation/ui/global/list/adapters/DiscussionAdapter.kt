package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
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
import com.akhbulatov.discusim.presentation.ui.global.utils.showDiscussionVoteProgress
import com.github.razir.progressbutton.bindProgressButton
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_discussion.*

private typealias OnVoteClickListener = (view: View, item: Discussion, position: Int) -> Unit
private typealias OnItemClickListener = (Discussion, position: Int) -> Unit

class DiscussionAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onVoteClickListener: OnVoteClickListener,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_DISCUSSION -> {
                val itemView = parent.inflate(R.layout.item_discussion)
                DiscussionViewHolder(itemView, lifecycleOwner, onVoteClickListener, onItemClickListener)
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

    class DiscussionViewHolder(
        itemView: View,
        lifecycleOwner: LifecycleOwner,
        private val onVoteClickListener: OnVoteClickListener,
        private val onItemClickListener: OnItemClickListener
    ) : BaseViewHolder<Any>(itemView) {

        private lateinit var discussion: Discussion

        init {
            lifecycleOwner.bindProgressButton(upvotesButton)
            upvotesButton.setOnClickListener {
                upvotesButton.showDiscussionVoteProgress(true, discussion.vote)
                onVoteClickListener(it, discussion, adapterPosition)
            }
            itemView.setOnClickListener { onItemClickListener(discussion, adapterPosition) }
        }

        override fun bind(item: Any) {
            if (item is Discussion) {
                discussion = item

                val context = itemView.context
                authorImageView.loadRoundedImage(context, item.author.avatarUrl)
                authorTextView.text = item.author.name
                dateTextView.text = item.createdAt.getHumanCreatedTime(itemView.resources)

                val hasMedia = item.mediaList.isNotEmpty()
                if (hasMedia) {
                    contentImageView.loadImage(context, item.mediaList.first().url)
                }
                contentImageView.isVisible = hasMedia

                if (item.topics.isNotEmpty()) {
                    if (topicChipGroup.childCount > 0) {
                        topicChipGroup.removeAllViews()
                    }

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
                upvotesButton.setDiscussionVote(item.vote)
                commentsButton.text = item.comments.toString()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
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