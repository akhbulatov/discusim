package com.akhbulatov.discusim.domain.global.models

import com.akhbulatov.discusim.domain.global.models.Action.Type
import com.akhbulatov.discusim.domain.global.models.forum.ForumShort
import com.akhbulatov.discusim.domain.global.models.user.UserShort
import org.threeten.bp.LocalDateTime

/**
 * Описывает активность. Например, *активность юзера*.
 *
 * Активностью может быть [Type.DISCUSSION_VOTE], [Type.COMMENT] или один из других вариантов [Type].
 */
data class Action(
    val discussionVote: DiscussionVote? = null,
    val comment: CommentShort? = null,
    val type: Type,
    val createdAt: LocalDateTime
) {

    /**
     * Сравнивает ID одного из объектов типа активности - [Type], т.к. у самого [Action] нет ID.
     */
    fun equalsId(other: Action): Boolean = IdComparator.compare(this, other) == 0

    data class DiscussionVote(
        val id: Long,
        val discussion: DiscussionShort,
        val forum: ForumShort,
        val author: UserShort
    )

    enum class Type {
        DISCUSSION_VOTE,
        COMMENT
    }

    object IdComparator : Comparator<Action> {
        override fun compare(o1: Action, o2: Action): Int {
            val firstDiscussionVote = o1.discussionVote
            val secondDiscussionVote = o2.discussionVote
            val firstComment = o1.comment
            val secondComment = o2.comment

            if (firstDiscussionVote != null && secondDiscussionVote != null) { // Discussion Vote
                return firstDiscussionVote.id.compareTo(secondDiscussionVote.id)
            } else if (firstComment != null && secondComment != null) { // Comment
                return firstComment.id.compareTo(secondComment.id)
            }
            throw IllegalArgumentException() // TODO
        }
    }
}