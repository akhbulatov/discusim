package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.ui.global.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.ProgressViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setVote
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_thread.*

class ThreadsAdapter : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_THREAD -> {
                val itemView = parent.inflate(R.layout.item_thread)
                ThreadViewHolder(itemView)
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
            is Thread -> ITEM_THREAD
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

    class ThreadViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun bind(item: Any) {
            if (item is Thread) {
                authorImageView.loadRoundedImage(itemView.context, item.author.avatarUrl)
                authorTextView.text = item.author.name
                dateTextView.text = item.createdAt.getHumanCreatedTime(itemView.resources)

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
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Thread && newItem is Thread) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                oldItem == newItem
        }

        private const val ITEM_THREAD = 0
        private const val ITEM_PROGRESS = 1
    }
}