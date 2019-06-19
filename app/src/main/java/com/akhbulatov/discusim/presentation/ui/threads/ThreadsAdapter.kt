package com.akhbulatov.discusim.presentation.ui.threads

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.ui.global.base.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
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
            item.let {
                val contentImageUrl = if (!it.media.isNullOrEmpty()) it.media[0].url else null

                Glide.with(itemView)
                    .load(it.author.avatar.small.link)
                    .into(authorImageView)

                Glide.with(itemView)
                    .load(contentImageUrl)
                    .into(contentImageView)

                authorTextView.text = it.author.name
                createdAtTextView.text = it.createdAt
                titleTextView.text = it.title
            }
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