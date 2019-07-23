package com.akhbulatov.discusim.data.global.network.models.vote

import com.akhbulatov.discusim.domain.global.models.Vote
import timber.log.Timber
import javax.inject.Inject

class VoteResponseMapper @Inject constructor() {

    fun map(response: VoteResponse): Vote.Type = map(response.vote.vote)

    fun map(upvotes: Int, vote: Int): Vote {
        val type = map(vote)
        return Vote(upvotes, type)
    }

    private fun map(vote: Int): Vote.Type = when (vote) {
        -1 -> Vote.Type.DOWNVOTE
        0 -> Vote.Type.NO_VOTE
        1 -> Vote.Type.UPVOTE
        else -> {
            Timber.w("Unknown vote type: $vote")
            Vote.Type.NO_VOTE
        }
    }

    fun map(voteType: Vote.Type): Int = when (voteType) {
        Vote.Type.NO_VOTE -> 0
        Vote.Type.UPVOTE -> 1
        Vote.Type.DOWNVOTE -> -1
    }
}
