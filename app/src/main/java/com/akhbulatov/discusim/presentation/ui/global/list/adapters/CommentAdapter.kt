package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.comment.Comment
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.presentation.ui.global.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.ProgressViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setTintEndDrawable
import kotlinx.android.synthetic.main.item_comment.*
import org.jetbrains.anko.dimen

class CommentAdapter : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_COMMENT -> {
                val itemView = parent.inflate(R.layout.item_comment)
                CommentViewHolder(itemView)
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
            is Comment -> ITEM_COMMENT
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

    private class CommentViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        override fun bind(item: Any) {
            if (item is Comment) {
                val context = itemView.context

                authorImageView.loadRoundedImage(context, item.author.avatarUrl)
                authorTextView.text = item.author.name
                dateTextView.text = item.createdAt.getHumanCreatedTime(context.resources)
                messageTextView.text = item.message

                if (item.vote.upvotes > 0) {
                    with(upvoteTextView) {
                        compoundDrawablePadding = context.dimen(R.dimen.spacing_micro)
                        text = item.vote.upvotes.toString()
                    }
                } else {
                    with(upvoteTextView) {
                        compoundDrawablePadding = 0
                        text = null
                    }
                }
                when (item.vote.type) {
                    Vote.Type.NO_VOTE -> {
                        upvoteTextView.setTintEndDrawable(R.color.button_comment_no_vote)
                        downvoteTextView.setTintEndDrawable(R.color.button_comment_no_vote)
                    }
                    Vote.Type.UPVOTE -> {
                        upvoteTextView.setTintEndDrawable(R.color.button_comment_upvote)
                        downvoteTextView.setTintEndDrawable(R.color.button_comment_no_vote)
                    }
                    Vote.Type.DOWNVOTE -> {
                        upvoteTextView.setTintEndDrawable(R.color.button_comment_no_vote)
                        downvoteTextView.setTintEndDrawable(R.color.button_comment_downvote)
                    }
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is Comment && newItem is Comment) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                oldItem == newItem
        }

        private const val ITEM_COMMENT = 0
        private const val ITEM_PROGRESS = 1
    }
}