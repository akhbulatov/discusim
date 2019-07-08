package com.akhbulatov.discusim.data.global.network.models.vote

import com.akhbulatov.discusim.domain.global.models.VoteType
import javax.inject.Inject

class VoteResponseMapper @Inject constructor() {
    fun map(response: VoteResponse): VoteType = map(response.vote)

    fun map(model: VoteNetModel): VoteType = map(model.vote)

    fun map(vote: Int): VoteType =
        when (vote) {
            -1 -> VoteType.DOWNVOTE
            0 -> VoteType.NO_VOTE
            1 -> VoteType.UPVOTE
            else -> throw IllegalArgumentException()
        }

    fun map(voteType: VoteType): Int = when (voteType) {
        VoteType.NO_VOTE -> 0
        VoteType.UPVOTE -> 1
        VoteType.DOWNVOTE -> -1
    }
}