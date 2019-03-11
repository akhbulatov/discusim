package com.akhbulatov.discusim.domain.global.models

data class Activity(
    val main: Main? = null,
    val post: Comment? = null,
    val type: Type,
    val createdAt: String
) {

    data class Main(
        val id: String,
        val voteType: VoteType,
        val thread: Thread,
        val forum: Forum,
        val author: User
    )

    enum class Type {
        THREAD_LIKE,
        POST
    }
}