package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.global.models.PagedList
import io.reactivex.Single

interface CommentRepository {
    fun getUserComments(userId: Long, cursor: String?): Single<PagedList<Comment>>
}