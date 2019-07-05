package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Action(
    val id: Long,
    val threadVote: ThreadVote? = null,
    val comment: CommentPreview? = null,
    val type: Type,
    val createdAt: LocalDateTime
) {

    data class ThreadVote(
        val id: Long,
        val thread: ThreadPreview,
        val forum: ForumPreview,
        val author: UserPreview
    )

    enum class Type {
        THREAD_VOTE,
        COMMENT
    }
}