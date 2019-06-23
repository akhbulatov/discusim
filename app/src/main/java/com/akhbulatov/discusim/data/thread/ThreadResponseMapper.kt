package com.akhbulatov.discusim.data.thread

import com.akhbulatov.discusim.data.global.network.models.ThreadNetModel
import com.akhbulatov.discusim.domain.global.models.Thread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThreadResponseMapper @Inject constructor() {
    fun map(model: ThreadNetModel): Thread =
        model.let {
            Thread(
                it.id,
                it.title
            )
        }

    fun map(response: ThreadsResponse): List<Thread> = response.threads.map { map(it) }
}