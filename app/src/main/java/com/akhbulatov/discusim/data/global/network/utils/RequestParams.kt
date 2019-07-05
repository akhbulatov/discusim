package com.akhbulatov.discusim.data.global.network.utils

object RequestParams {
    object Forum {
        /** Запрашивает статус подписи на форум. */
        const val FOLLOWS_FORUM = "followsForum"

        /** Запрашивает кол-во тредов, фолловеров и т.д. форума. */
        const val COUNTERS = "counters"
    }

    object Thread {
        /** Запрашивает объект автора (юзера). */
        const val AUTHOR = "author"

        /** Запрашивает список топиков. */
        const val TOPICS = "topics"
    }

    object Comment {
        /** Запрашивает объект треда коммента. */
        const val THREAD = "thread"
    }
}