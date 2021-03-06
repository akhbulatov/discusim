package com.akhbulatov.discusim.presentation.ui.forum.discussions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.discussion.DiscussionInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.PagedList
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.models.discussion.Discussion
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Paginator
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ForumDiscussionsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val discussionInteractor: DiscussionInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private lateinit var forumId: String
    private lateinit var discussionType: DiscussionType

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _discussions = MutableLiveData<Pair<Boolean, List<Discussion>>>()
    val discussions: LiveData<Pair<Boolean, List<Discussion>>> get() = _discussions

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _pageProgress = MutableLiveData<Boolean>()
    val pageProgress: LiveData<Boolean> get() = _pageProgress

    private val _vote = MutableLiveData<Vote>()
    val vote: LiveData<Vote> get() = _vote

    private val _voteError = SingleLiveEvent<String>()
    val voteError: LiveData<String> get() = _voteError

    private val paginator = Paginator(
        {
            chooseDiscussionsRequest(it)
                .observeOn(schedulers.ui())
        },
        object : Paginator.ViewController<Discussion> {
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

            override fun showData(show: Boolean, data: List<Discussion>) {
                _discussions.value = Pair(show, data)
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

    private fun chooseDiscussionsRequest(cursor: String?): Single<PagedList<Discussion>> =
        when (discussionType) {
            DiscussionType.LATEST -> discussionInteractor.getDiscussions(forumId, cursor)
            DiscussionType.HOT -> discussionInteractor.getHotDiscussions(forumId)
            DiscussionType.POPULAR -> discussionInteractor.getPopularDiscussions(forumId)
        }

    fun setParams(forumId: String, discussionType: DiscussionType) {
        this.forumId = forumId
        this.discussionType = discussionType
        refreshDiscussions()
    }

    fun refreshDiscussions() = paginator.refresh()
    fun loadNextDiscussionsPage() = paginator.loadNewPage()

    fun onAuthorClicked(user: User) = router.startFlow(Screens.UserFlow(user.id))

    fun onVoteClicked(discussion: Discussion, upvoted: Boolean) {
        // Дизлайк не предусмотрен
        val voteType = if (!upvoted) Vote.Type.UPVOTE else Vote.Type.NO_VOTE
        disposables += discussionInteractor.voteDiscussion(discussion.id, voteType)
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = { _vote.value = updatedVote(discussion, it) },
                onError = { errorHandler.proceed(it) { msg -> _voteError.value = msg } }
            )
    }

    private fun updatedVote(discussion: Discussion, voteType: Vote.Type): Vote {
        return Vote.createUpdatedInstance(discussion.vote.upvotes, voteType)
    }

    fun onDiscussionClicked(discussion: Discussion) {
        router.navigateTo(Screens.DiscussionDetails(discussion.id))
    }

    override fun onCleared() {
        paginator.release()
        super.onCleared()
    }

    override fun onBackPressed() = router.exit()
}
