package com.akhbulatov.discusim.presentation.ui.thread.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.thread.ThreadInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ThreadDetailsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val threadInteractor: ThreadInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private var threadId: Long = 0

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _thread = MutableLiveData<Pair<Boolean, Thread?>>()
    val thread: LiveData<Pair<Boolean, Thread?>> get() = _thread

    fun setThreadId(threadId: Long) {
        this.threadId = threadId
        loadThreadDetails()
    }

    fun loadThreadDetails() {
        subscriptions += threadInteractor.getThreadDetails(threadId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _thread.value = Pair(false, null)
                _error.value = Pair(false, null)
                _progress.value = true
            }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = { _thread.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _error.value = Pair(true, msg) } }
            )
    }

    override fun onBackPressed() = router.exit()
}