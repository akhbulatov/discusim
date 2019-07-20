package com.akhbulatov.discusim.presentation.ui.user.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.following.FollowingInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Paginator
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.SingleLiveEvent
import javax.inject.Inject

class UserFollowingViewModel @Inject constructor(
    private val router: FlowRouter,
    private val followingInteractor: FollowingInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private var userId: Long = 0

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _following = MutableLiveData<Pair<Boolean, List<User>>>()
    val following: LiveData<Pair<Boolean, List<User>>> get() = _following

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _pageProgress = MutableLiveData<Boolean>()
    val pageProgress: LiveData<Boolean> get() = _pageProgress

    private val paginator = Paginator(
        {
            followingInteractor.getUserFollowing(userId, it)
                .observeOn(schedulers.ui())
        },
        object : Paginator.ViewController<User> {
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

            override fun showData(show: Boolean, data: List<User>) {
                _following.value = Pair(show, data)
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

    fun setUserId(userId: Long) {
        this.userId = userId
        paginator.refresh()
    }

    fun refreshFollowing() = paginator.refresh()
    fun loadNextFollowingPage() = paginator.loadNewPage()

    fun onFollowingClicked(user: User) = router.startFlow(Screens.UserFlow(user.id))

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }

    override fun onBackPressed() = router.exit()
}