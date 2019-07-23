package com.akhbulatov.discusim.data.comment

import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.utils.RequestParams
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.comment.Comment
import com.akhbulatov.discusim.domain.global.repositories.CommentRepository
import io.reactivex.Single
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val api: DisqusApi,
    private val commentResponseMapper: CommentResponseMapper,
    private val schedulers: SchedulersProvider
) : CommentRepository {

    override fun getUserComments(userId: Long, cursor: String?): Single<PagedList<Comment>> =
        api.getUserComments(
            userId,
            cursor,
            listOf(RequestParams.Comment.DISCUSSION)
        )
            .map { commentResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}
