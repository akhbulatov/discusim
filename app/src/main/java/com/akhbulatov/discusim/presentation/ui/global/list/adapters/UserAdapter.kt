package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.ui.global.list.ProgressItem
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.ProgressViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import kotlinx.android.synthetic.main.item_user.*

class UserAdapter(
    private val onClickListener: (User) -> Unit
) : ListAdapter<Any, BaseViewHolder<Any>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> =
        when (viewType) {
            ITEM_USER -> {
                val itemView = parent.inflate(R.layout.item_user)
                UserViewHolder(itemView)
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
            is User -> ITEM_USER
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

    inner class UserViewHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        private lateinit var user: User

        init {
            itemView.setOnClickListener { onClickListener(user) }
        }

        override fun bind(item: Any) {
            if (item is User) {
                user = item

                nameTextView.text = item.name
                avatarImageView.loadRoundedImage(itemView.context, item.avatarUrl)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
                if (oldItem is User && newItem is User) {
                    oldItem.id == newItem.id
                } else {
                    oldItem is ProgressItem && newItem is ProgressItem
                }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
                oldItem == newItem
        }

        private const val ITEM_USER = 0
        private const val ITEM_PROGRESS = 1
    }
}