package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Action(
    val id: Long,
    val discussionVote: DiscussionVote? = null,
    val comment: CommentPreview? = null,
    val type: Type,
    val createdAt: LocalDateTime
) {

    data class DiscussionVote(
        val id: Long,
        val discussion: DiscussionPreview,
        val forum: ForumPreview,
        val author: UserPreview
    )

    enum class Type {
        DISCUSSION_VOTE,
        COMMENT
    }
}