package com.akhbulatov.discusim.data.global.network.utils

object RequestParams {
    object Forum {
        /** Запрашивает статус подписи на форум. */
        const val FOLLOWS_FORUM = "followsForum"

        /** Запрашивает кол-во тредов, фолловеров и т.д. форума. */
        const val COUNTERS = "counters"
    }

    object Thread {
        const val FORUM = "forum"
        const val AUTHOR = "author"
    }
}