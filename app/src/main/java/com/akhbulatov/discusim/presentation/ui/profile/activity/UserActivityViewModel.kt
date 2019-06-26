package com.akhbulatov.discusim.presentation.ui.profile.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.activity.ActivityInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.eventbus.CursorStore
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Paginator
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserActivityViewModel @Inject constructor(
    private val router: FlowRouter,
    private val sessionInteractor: SessionInteractor,
    private val activityInteractor: ActivityInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    cursorStore: CursorStore
) : BaseViewModel() {

    init {
        subscriptions += cursorStore.observe()
            .subscribeBy(
                onNext = {
                    Timber.d("Next cursor: $it")
                    paginator.nextPage = it
                },
                onComplete = { Timber.d("Observation cursor completed.") },
                onError = { Timber.e("An error occurred while observing cursor: $it") }
            )
    }

    private var userId: Long = -1

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _actions = MutableLiveData<Pair<Boolean, List<Action>>>()
    val actions: LiveData<Pair<Boolean, List<Action>>> get() = _actions

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _pageProgress = MutableLiveData<Boolean>()
    val pageProgress: LiveData<Boolean> get() = _pageProgress

    private val paginator = Paginator(
        {
            activityInteractor.getUserActivity(userId, it)
                .observeOn(schedulers.ui())
        },
        object : Paginator.ViewController<Action> {
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

            override fun showData(show: Boolean, data: List<Action>) {
                _actions.value = Pair(show, data)
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

    fun setUserId(userId: Long?) {
        this.userId = userId ?: sessionInteractor.getUserId()
        refreshActivity()
    }

    fun refreshActivity() = paginator.refresh()
    fun loadNextActivityPage() = paginator.loadNewPage()

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }

    override fun onBackPressed() = router.exit()
}