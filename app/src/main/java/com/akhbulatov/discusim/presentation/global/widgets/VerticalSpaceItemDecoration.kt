package com.akhbulatov.discusim.presentation.global.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class VerticalSpaceItemDecoration(private val height: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = height
    }
}