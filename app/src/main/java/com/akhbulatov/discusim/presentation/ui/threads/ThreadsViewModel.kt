package com.akhbulatov.discusim.presentation.ui.threads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.thread.ThreadInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ThreadsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val threadInteractor: ThreadInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _threads = MutableLiveData<List<Thread>>()
    val threads: LiveData<List<Thread>> get() = _threads

    private val _content = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _content

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun setParams(forumId: String?, threadType: ThreadType) {
        loadThreads(forumId, threadType)
    }

    private fun loadThreads(forumId: String?, threadType: ThreadType) {
        val threadsRequest = chooseThreadsRequest(forumId, threadType)
        subscriptions += threadsRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _content.value = false
                _progress.value = true
            }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = {
                    _content.value = true
                    _threads.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _error.value = msg } }
            )
    }

    private fun chooseThreadsRequest(forumId: String?, threadType: ThreadType): Single<List<Thread>> =
        when (threadType) {
            ThreadType.TRENDS -> threadInteractor.getTrendThreads(forumId)
            ThreadType.LATEST -> threadInteractor.getThreads(forumId!!)
            ThreadType.HOT -> threadInteractor.getHotThreads(forumId!!)
            ThreadType.POPULAR -> threadInteractor.getPopularThreads(forumId!!)
        }

    override fun onBackPressed() = router.exit()
}