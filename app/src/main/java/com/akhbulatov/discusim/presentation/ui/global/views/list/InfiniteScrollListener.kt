package com.akhbulatov.discusim.presentation.ui.global.views.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val onLoadMoreListener: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dx == 0 && dy == 0) return

        if (!loading && isScrolledToEnd()) {
            onLoadMoreListener()
            loading = true
        }
    }

    private fun isScrolledToEnd(): Boolean {
        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        return totalItemCount <= lastVisibleItemPosition + VISIBLE_THRESHOLD
    }

    fun setLoaded() {
        loading = false
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}