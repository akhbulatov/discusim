package com.akhbulatov.discusim.data.global.network.utils

object RequestParams {
    object Forum {
        /** Запрашивает статус подписи на форум. */
        const val FOLLOWS_FORUM = "followsForum"

        /** Запрашивает кол-во дискуссий, фолловеров и т.д. форума. */
        const val COUNTERS = "counters"
    }

    object Discussion {
        /** Запрашивает объект автора (юзера). */
        const val AUTHOR = "author"

        /** Запрашивает список топиков. */
        const val TOPICS = "topics"
    }

    object Comment {
        /** Запрашивает объект дискуссии, под которым написан коммент. */
        const val DISCUSSION = "thread"
    }
}