package com.akhbulatov.discusim.presentation.ui.global.views.list

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class DividerNoLastItemDecoration(
    context: Context,
    orientation: Int
) : DividerItemDecoration(context, orientation) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter!!.itemCount
        // Скрывает разделитель для последнего элемента
        if (position == itemCount - 1) {
            outRect.setEmpty()
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }
}