package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.comment.Comment
import io.reactivex.Single

interface CommentRepository {
    fun getUserComments(userId: Long, cursor: String?): Single<PagedList<Comment>>
}
