package com.akhbulatov.discusim.data.global.network.models.vote

import com.akhbulatov.discusim.domain.global.models.Vote
import javax.inject.Inject

class VoteResponseMapper @Inject constructor() {
    fun map(response: VoteResponse): Vote = map(response.vote)

    fun map(model: VoteNetModel): Vote = map(model.thread.likes, model.vote)

    fun map(upvotes: Int, vote: Int): Vote {
        val type = when (vote) {
            -1 -> Vote.Type.DOWNVOTE
            0 -> Vote.Type.NO_VOTE
            1 -> Vote.Type.UPVOTE
            else -> throw IllegalArgumentException("vote = $vote")
        }
        return Vote(upvotes, type)
    }

    fun map(voteType: Vote.Type): Int = when (voteType) {
        Vote.Type.NO_VOTE -> 0
        Vote.Type.UPVOTE -> 1
        Vote.Type.DOWNVOTE -> -1
    }
}
