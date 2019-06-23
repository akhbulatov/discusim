package com.akhbulatov.discusim.domain.global.models

import org.threeten.bp.LocalDateTime

data class Action(
    val threadVote: ThreadVote? = null,
    val comment: Comment? = null,
    val type: Type,
    val createdAt: LocalDateTime
) {

    data class ThreadVote(
        val id: Long,
        val thread: Thread,
        val forum: Forum,
        val author: User
    )

    enum class Type {
        THREAD_VOTE,
        POST
    }
}