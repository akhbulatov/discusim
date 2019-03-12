package com.akhbulatov.discusim.data.threads

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.squareup.moshi.Json

data class ThreadsResponse(@Json(name = "response") val threads: List<ThreadNetModel>)