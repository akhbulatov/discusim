package com.akhbulatov.discusim.presentation.ui.threads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.threads.ThreadsInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ThreadsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ThreadsInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _threads = MutableLiveData<List<Thread>>()
    val threads: LiveData<List<Thread>> get() = _threads

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setParams(forumId: String, threadType: ThreadType) {
        loadThreads(forumId, threadType)
    }

    private fun loadThreads(forumId: String, threadType: ThreadType) {
        val threadsRequest = when (threadType) {
            ThreadType.LATEST -> interactor.getThreads(forumId)
            ThreadType.HOT -> interactor.getHotThreads(forumId)
            ThreadType.POPULAR -> interactor.getPopularThreads(forumId)
        }

        subscriptions += threadsRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _threads.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}