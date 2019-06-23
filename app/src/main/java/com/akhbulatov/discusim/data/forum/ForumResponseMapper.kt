package com.akhbulatov.discusim.data.forum

import com.akhbulatov.discusim.data.global.network.models.ForumNetModel
import com.akhbulatov.discusim.domain.global.models.Forum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForumResponseMapper @Inject constructor() {
    fun map(model: ForumNetModel): Forum =
        model.let {
            Forum(
                it.pk,
                it.name
            )
        }

    fun map(response: ForumsResponse): List<Forum> = response.forums.map { map(it) }

    fun map(response: ForumResponse): Forum = map(response.forum)
}