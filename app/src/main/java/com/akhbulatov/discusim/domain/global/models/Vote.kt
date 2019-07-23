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

    companion object {
        /**
         * Создается обновленный экземпляр [Vote].
         *
         * @param oldUpvotes Старое кол-во голосов, которое обновляется в зависимости от [type].
         * @param type Тип голоса.
         * @return обновленный экземпляр [Vote].
         */
        fun createUpdatedInstance(oldUpvotes: Int, type: Type): Vote {
            val newUpvotes = when (type) {
                Type.NO_VOTE, Type.DOWNVOTE -> oldUpvotes - 1
                Type.UPVOTE -> oldUpvotes + 1
            }
            return Vote(newUpvotes, type)
        }
    }
}
