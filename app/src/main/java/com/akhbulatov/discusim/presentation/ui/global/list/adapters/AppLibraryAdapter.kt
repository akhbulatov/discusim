package com.akhbulatov.discusim.presentation.ui.global.list.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.AppLibrary
import com.akhbulatov.discusim.presentation.ui.global.list.viewholders.BaseViewHolder
import com.akhbulatov.discusim.presentation.ui.global.utils.inflate
import kotlinx.android.synthetic.main.item_app_library.*

class AppLibraryAdapter(
    private val onItemClickListener: (AppLibrary) -> Unit
) : ListAdapter<AppLibrary, AppLibraryAdapter.AppLibraryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppLibraryViewHolder {
        val itemView = parent.inflate(R.layout.item_app_library)
        return AppLibraryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AppLibraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppLibraryViewHolder(itemView: View) : BaseViewHolder<AppLibrary>(itemView) {

        private lateinit var appLibrary: AppLibrary

        init {
            itemView.setOnClickListener { onItemClickListener(appLibrary) }
        }

        override fun bind(item: AppLibrary) {
            appLibrary = item

            nameTextView.text = item.name
            authorTextView.text = item.author
            licenseTextView.text = item.license
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AppLibrary>() {
            override fun areItemsTheSame(oldItem: AppLibrary, newItem: AppLibrary): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: AppLibrary, newItem: AppLibrary): Boolean =
                oldItem == newItem
        }
    }
}
