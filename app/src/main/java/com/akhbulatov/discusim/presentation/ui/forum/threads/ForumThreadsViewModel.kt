package com.akhbulatov.discusim.presentation.ui.forum.threads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.domain.thread.ThreadInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Paginator
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.Single
import javax.inject.Inject

class ForumThreadsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val threadInteractor: ThreadInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private lateinit var forumId: String
    private lateinit var threadType: ThreadType

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _threads = MutableLiveData<Pair<Boolean, List<Thread>>>()
    val threads: LiveData<Pair<Boolean, List<Thread>>> get() = _threads

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _pageProgress = MutableLiveData<Boolean>()
    val pageProgress: LiveData<Boolean> get() = _pageProgress

    private val paginator = Paginator(
        {
            chooseThreadsRequest()
                .observeOn(schedulers.ui())
        },
        object : Paginator.ViewController<Thread> {
            override fun showEmptyProgress(show: Boolean) {
                _emptyProgress.value = show
            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                if (error != null) {
                    errorHandler.proceed(error) { msg -> _emptyError.value = Pair(show, msg) }
                } else {
                    _emptyError.value = Pair(show, null)
                }
            }

            override fun showEmptyData(show: Boolean) {
                _emptyData.value = show
            }

            override fun showData(show: Boolean, data: List<Thread>) {
                _threads.value = Pair(show, data)
            }

            override fun showErrorMessage(error: Throwable) {
                errorHandler.proceed(error) { msg -> _errorMessage.value = msg }
            }

            override fun showRefreshProgress(show: Boolean) {
                _refreshProgress.value = show
            }

            override fun showPageProgress(show: Boolean) {
                _pageProgress.value = show
            }
        }
    )

    private fun chooseThreadsRequest(): Single<PagedList<Thread>> =
        when (threadType) {
            ThreadType.LATEST -> threadInteractor.getThreads(forumId)
            ThreadType.HOT -> threadInteractor.getHotThreads(forumId)
            ThreadType.POPULAR -> threadInteractor.getPopularThreads(forumId)
        }

    fun setParams(forumId: String, threadType: ThreadType) {
        this.forumId = forumId
        this.threadType = threadType
        refreshThreads()
    }

    fun refreshThreads() = paginator.refresh()
    fun loadNextThreadsPage() = paginator.loadNewPage()

    fun onThreadClicked(thread: Thread) = router.navigateTo(Screens.ThreadDetails(thread.id))

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }

    override fun onBackPressed() = router.exit()
}