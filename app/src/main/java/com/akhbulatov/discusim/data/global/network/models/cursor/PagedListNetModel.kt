package com.akhbulatov.discusim.data.global.network.models.cursor

data class PagedListNetModel<T>(
    val cursor: CursorNetModel,
    val data: List<T>
)
