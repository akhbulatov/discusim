package com.akhbulatov.discusim.domain.global.models

data class Vote(
    val upvotes: Int,
    val type: Type
) {

    enum class Type {
        NO_VOTE,
        UPVOTE,
        DOWNVOTE
    }
}
