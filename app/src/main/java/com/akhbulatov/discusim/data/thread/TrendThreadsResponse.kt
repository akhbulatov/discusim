package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendThreadsResponse(@Json(name = "response") val trends: List<TrendThreadNetModel>) {

    @JsonClass(generateAdapter = true)
    data class TrendThreadNetModel(@Json(name = "thread") val thread: ThreadNetModel)
}