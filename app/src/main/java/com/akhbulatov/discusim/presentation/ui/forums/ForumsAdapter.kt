package com.akhbulatov.discusim.presentation.ui.forums

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.views.list.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.views.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.views.list.ProgressViewHolder
import kotlinx.android.synthetic.main.item_forum.*

class ForumsAdapter : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_FORUM -> {
                val itemView = parent.inflate(R.layout.item_forum)
                ForumViewHolder(itemView)
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
            is Forum -> ITEM_FORUM
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

    class ForumViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun bind(item: Any) {
            if (item is Forum) {
                nameTextView.text = item.name
                val faviconUrl = item.channel?.avatarUrl ?: item.faviconUrl
                avatarImageView.loadRoundedImage(itemView.context, faviconUrl)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Forum && newItem is Forum) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Forum && newItem is Forum) {
                    oldItem == newItem
                } else {
                    true
                }
        }

        private const val ITEM_FORUM = 0
        private const val ITEM_PROGRESS = 1
    }
}