package com.akhbulatov.discusim.domain.comment

import com.akhbulatov.discusim.domain.global.models.comment.Comment
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.repositories.CommentRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentInteractor @Inject constructor(
    private val commentRepository: CommentRepository
) {

    fun getUserComments(userId: Long, cursor: String?): Single<PagedList<Comment>> =
        commentRepository.getUserComments(userId, cursor)
}