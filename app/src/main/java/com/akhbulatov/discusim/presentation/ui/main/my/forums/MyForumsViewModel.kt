package com.akhbulatov.discusim.presentation.ui.main.my.forums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.forum.ForumInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Paginator
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.SingleLiveEvent
import javax.inject.Inject

class MyForumsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val forumInteractor: ForumInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _forums = MutableLiveData<Pair<Boolean, List<Forum>>>()
    val forums: LiveData<Pair<Boolean, List<Forum>>> get() = _forums

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _pageProgress = MutableLiveData<Boolean>()
    val pageProgress: LiveData<Boolean> get() = _pageProgress

    private val paginator = Paginator(
        {
            forumInteractor.getMyFollowingForums(it)
                .observeOn(schedulers.ui())
        },
        object : Paginator.ViewController<Forum> {
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

            override fun showData(show: Boolean, data: List<Forum>) {
                _forums.value = Pair(show, data)
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

    init {
        paginator.refresh()
    }

    fun refreshForums() = paginator.refresh()
    fun loadNextForumsPage() = paginator.loadNewPage()

    fun onForumClicked(forum: Forum) = router.startFlow(Screens.ForumFlow(forum.id))

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }

    override fun onBackPressed() = router.exit()
}
