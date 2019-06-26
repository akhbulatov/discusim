package com.akhbulatov.discusim.presentation.ui.global.views.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class VerticalSpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter!!.itemCount
        // Добавляет отступы для всех элементов, кроме последнего
        if (position != itemCount - 1) {
            outRect.bottom = space
        }
    }
}