package com.akhbulatov.discusim.data.discussion

import com.akhbulatov.discusim.data.global.network.models.DiscussionNetModel
import com.akhbulatov.discusim.data.global.network.models.DiscussionShortNetModel
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponseMapper
import com.akhbulatov.discusim.data.topic.TopicResponseMapper
import com.akhbulatov.discusim.data.user.UserResponseMapper
import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.DiscussionShort
import com.akhbulatov.discusim.domain.global.models.PagedList
import javax.inject.Inject

class DiscussionResponseMapper @Inject constructor(
    private val userResponseMapper: UserResponseMapper,
    private val topicResponseMapper: TopicResponseMapper,
    private val voteResponseMapper: VoteResponseMapper
) {

    fun map(response: DiscussionResponse): Discussion = map(response.discussion)

    fun map(response: DiscussionsResponse): PagedList<Discussion> {
        val discussions = response.discussions.map { map(it) }
        return PagedList(response.cursor?.next, discussions)
    }

    fun map(model: DiscussionNetModel): Discussion =
        model.let {
            Discussion(
                it.id.toLong(),
                it.title,
                if (it.message.isNotEmpty()) it.message else null,
                it.media?.let { list -> list.map { media -> Discussion.Media(media.url) } } ?: emptyList(),
                userResponseMapper.map(it.author),
                it.createdAt,
                it.topics?.let { topic -> topicResponseMapper.map(topic) } ?: emptyList(),
                voteResponseMapper.map(it.likes, it.userScore),
                it.posts,
                it.link
            )
        }

    fun map(model: DiscussionShortNetModel): DiscussionShort =
        model.let {
            DiscussionShort(
                it.id.toLong(),
                it.title,
                it.link
            )
        }
}