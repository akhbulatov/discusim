package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Action(
    val id: Long,
    val discussionVote: DiscussionVote? = null,
    val comment: CommentShort? = null,
    val type: Type,
    val createdAt: LocalDateTime
) {

    data class DiscussionVote(
        val id: Long,
        val discussion: DiscussionShort,
        val forum: ForumShort,
        val author: UserShort
    )

    enum class Type {
        DISCUSSION_VOTE,
        COMMENT
    }
}