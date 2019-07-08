package com.akhbulatov.discusim.presentation.ui.discussion.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.discussion.DiscussionInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class DiscussionDetailsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val discussionInteractor: DiscussionInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private var discussionId: Long = 0

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _discussion = MutableLiveData<Pair<Boolean, Discussion?>>()
    val discussion: LiveData<Pair<Boolean, Discussion?>> get() = _discussion

    private val _voteProgress = MutableLiveData<Boolean>()
    val voteProgress: LiveData<Boolean> get() = _voteProgress

    private val _voteError = MutableLiveData<String>()
    val voteError: LiveData<String> get() = _voteError

    private val _vote = MutableLiveData<Vote>()
    val vote: LiveData<Vote> get() = _vote

    fun setDiscussionId(discussionId: Long) {
        this.discussionId = discussionId
        loadDiscussionDetails()
    }

    fun loadDiscussionDetails() {
        disposables += discussionInteractor.getDiscussionDetails(discussionId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _discussion.value = Pair(false, null)
                _emptyError.value = Pair(false, null)
                _emptyProgress.value = true
            }
            .doAfterTerminate { _emptyProgress.value = false }
            .subscribeBy(
                onSuccess = { _discussion.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _emptyError.value = Pair(true, msg) } }
            )
    }

    fun onVoteClicked(upvoted: Boolean) {
        // Дизлайк не предусмотрен
        val voteType = if (!upvoted) Vote.Type.UPVOTE else Vote.Type.NO_VOTE
        disposables += discussionInteractor.voteDiscussion(discussionId, voteType)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _voteProgress.value = true }
            .doAfterTerminate { _voteProgress.value = false }
            .subscribeBy(
                onSuccess = { _vote.value = it },
                onError = { errorHandler.proceed(it) { msg -> _voteError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}