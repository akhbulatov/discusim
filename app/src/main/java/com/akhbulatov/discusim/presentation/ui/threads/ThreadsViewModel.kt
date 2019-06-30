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

    private var forumId: String? = null
    private lateinit var threadType: ThreadType

    private val _threads = MutableLiveData<List<Thread>>()
    val threads: LiveData<List<Thread>> get() = _threads

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _refreshError = MutableLiveData<String>()
    val refreshError: LiveData<String> get() = _refreshError

    fun setParams(forumId: String?, threadType: ThreadType) {
        this.forumId = forumId
        this.threadType = threadType
        loadThreads()
    }

    private fun loadThreads(refresh: Boolean = false) {
        val threadsRequest = chooseThreadsRequest(forumId, threadType)
        subscriptions += threadsRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                if (!refresh) {
                    _progress.value = true
                } else {
                    _refreshProgress.value = true
                }
            }
            .doAfterTerminate {
                if (!refresh) {
                    _progress.value = false
                } else {
                    _refreshProgress.value = false
                }
            }
            .subscribeBy(
                onSuccess = {
                    if (_error.value?.first != false) {
                        _error.value = Pair(false, null)
                    }
                    _threads.value = it
                },
                onError = {
                    errorHandler.proceed(it) { msg ->
                        if (!refresh) {
                            _error.value = Pair(true, msg)
                        } else {
                            _refreshError.value = msg
                        }
                    }
                }
            )
    }

    private fun chooseThreadsRequest(forumId: String?, threadType: ThreadType): Single<List<Thread>> =
        when (threadType) {
            ThreadType.LATEST -> threadInteractor.getThreads(forumId!!)
            ThreadType.HOT -> threadInteractor.getHotThreads(forumId!!)
            ThreadType.POPULAR -> threadInteractor.getPopularThreads(forumId!!)
        }

    fun refreshThreads() = loadThreads(refresh = true)

    override fun onBackPressed() = router.exit()
}