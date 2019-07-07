package com.akhbulatov.discusim.presentation.ui.discussion.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.discussion.DiscussionInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Discussion
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

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _discussion = MutableLiveData<Pair<Boolean, Discussion?>>()
    val discussion: LiveData<Pair<Boolean, Discussion?>> get() = _discussion

    fun setDiscussionId(discussionId: Long) {
        this.discussionId = discussionId
        loadDiscussionDetails()
    }

    fun loadDiscussionDetails() {
        disposables += discussionInteractor.getDiscussionDetails(discussionId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _discussion.value = Pair(false, null)
                _error.value = Pair(false, null)
                _progress.value = true
            }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = { _discussion.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _error.value = Pair(true, msg) } }
            )
    }

    override fun onBackPressed() = router.exit()
}