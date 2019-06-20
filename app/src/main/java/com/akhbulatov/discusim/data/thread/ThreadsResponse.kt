package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.squareup.moshi.Json

data class ThreadsResponse(@Json(name = "response") val threads: List<ThreadNetModel>)