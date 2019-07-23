package com.akhbulatov.discusim.domain.global.models

data class PagedList<T>(
    val cursor: String?,
    val data: List<T>
)
