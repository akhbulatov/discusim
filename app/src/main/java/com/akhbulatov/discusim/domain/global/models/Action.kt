package com.akhbulatov.discusim.domain.global.models

data class Action(
    val threadVote: ThreadVote? = null,
    val comment: Comment? = null,
    val type: Type,
    val createdAt: String
) {

    data class ThreadVote(
        val id: Long,
        val thread: Thread,
        val forum: Forum,
        val author: User,
        val voteType: VoteType
    )

    enum class Type {
        THREAD_VOTE,
        POST
    }
}