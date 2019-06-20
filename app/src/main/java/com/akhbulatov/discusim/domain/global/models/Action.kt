package com.akhbulatov.discusim.domain.global.models

data class Action(
    val threadVote: ThreadVote? = null,
    val post: Post? = null,
    val type: Type,
    val createdAt: String
) {

    data class ThreadVote(
        val id: String,
        val voteType: VoteType,
        val thread: Thread,
        val forum: Forum,
        val author: User
    ) {

        data class Thread(
            val id: Long,
            val title: String
        )
    }

    enum class Type {
        THREAD_LIKE,
        POST
    }
}