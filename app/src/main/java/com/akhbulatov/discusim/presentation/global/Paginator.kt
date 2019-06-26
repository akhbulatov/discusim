package com.akhbulatov.discusim.presentation.global

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class Paginator<T>(
    private val requestFactory: (String?) -> Single<List<T>>,
    private val viewController: ViewController<T>
) {

    private val firstPage: String? = null
    var nextPage: String? = null

    private var currentState: State<T> = EmptyState()
    private val currentData = mutableListOf<T>()
    private var requestDisposable: Disposable? = null

    fun restart() = currentState.restart()
    fun refresh() = currentState.refresh()
    fun loadNewPage() = currentState.loadNewPage()
    fun release() = currentState.release()

    private fun loadPage(page: String?) {
        requestDisposable?.dispose()
        requestDisposable = requestFactory.invoke(page)
            .subscribeBy(
                onSuccess = { currentState.newData(it) },
                onError = { currentState.fail(it) }
            )
    }

    /**
     * Callback'и, вызываемые в момент перехода от одного стейта к другому.
     */
    interface ViewController<T> {
        fun showEmptyProgress(show: Boolean)
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyData(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList())
        fun showErrorMessage(error: Throwable)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
    }

    private interface State<T> {
        fun restart() {}
        fun refresh() {}
        fun loadNewPage() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
        fun release() {}
    }

    /**
     * Первоначальное состояние.
     */
    private inner class EmptyState : State<T> {
        override fun refresh() {
            Timber.d("refresh")
            currentState = EmptyProgressState()

            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние отображения прогресса на пустом экране.
     */
    private inner class EmptyProgressState : State<T> {
        override fun restart() {
            Timber.d("restart")
            loadPage(firstPage)
        }

        override fun newData(data: List<T>) {
            Timber.d("newData: ${data.size}")
            if (data.isNotEmpty()) {
                currentState = DataState()

                currentData.clear()
                currentData.addAll(data)
                viewController.showData(true, currentData)
                viewController.showEmptyProgress(false)

            } else {
                currentState = EmptyDataState()

                viewController.showEmptyProgress(false)
                viewController.showEmptyData(true)
            }
        }

        override fun fail(error: Throwable) {
            Timber.d("fail: ${error.javaClass.simpleName}")
            currentState = EmptyErrorState()

            viewController.showEmptyProgress(false)
            viewController.showEmptyError(true, error)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние отображения ошибки на пустом экране.
     */
    private inner class EmptyErrorState : State<T> {
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun refresh() {
            Timber.d("refresh")
            currentState = EmptyProgressState()

            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние отображения пустых данных (список пуст).
     */
    private inner class EmptyDataState : State<T> {
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showEmptyData(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun refresh() {
            Timber.d("refresh")
            currentState = EmptyProgressState()

            viewController.showEmptyData(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние отображения данных списка (НЕ всего).
     */
    private inner class DataState : State<T> {
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showData(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun refresh() {
            Timber.d("refresh")
            currentState = RefreshState()

            viewController.showRefreshProgress(true)
            loadPage(firstPage)
        }

        override fun loadNewPage() {
            Timber.d("loadNewPage: $nextPage")
            currentState = PageProgressState()

            viewController.showPageProgress(true)
            loadPage(nextPage)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние обновления списка. Например, кнопка "Обновить" на пустом экране.
     */
    private inner class RefreshState : State<T> { // TODO
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showData(false)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun newData(data: List<T>) {
            Timber.d("newData: ${data.size}")
            if (data.isNotEmpty()) {
                currentState = DataState()

                currentData.clear()
                currentData.addAll(data)

                nextPage = firstPage

                viewController.showRefreshProgress(false)
                viewController.showData(true, data)
            } else {
                currentState = EmptyDataState()

                currentData.clear()
                viewController.showData(false)
                viewController.showRefreshProgress(false)
                viewController.showEmptyData(true)
            }
        }

        override fun fail(error: Throwable) {
            Timber.d("fail: ${error.javaClass.simpleName}")
            currentState = DataState()

            viewController.showRefreshProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние загружения новой (следующей) страницы.
     */
    private inner class PageProgressState : State<T> {
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showData(false)
            viewController.showPageProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun newData(data: List<T>) {
            Timber.d("newData: ${data.size}")
            if (data.isNotEmpty()) {
                currentState = DataState()

                currentData.addAll(data)
                viewController.showPageProgress(false)
                viewController.showData(true, currentData)
            } else {
                currentState = AllDataState()

                viewController.showPageProgress(false)
            }
        }

        override fun refresh() {
            Timber.d("refresh")
            currentState = RefreshState()

            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            loadPage(firstPage)
        }

        override fun fail(error: Throwable) {
            Timber.d("fail: ${error.javaClass.simpleName}")
            currentState = DataState()

            viewController.showPageProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние загруженного всего списка (всех страниц).
     */
    private inner class AllDataState : State<T> {
        override fun restart() {
            Timber.d("restart")
            currentState = EmptyProgressState()

            viewController.showData(false)
            viewController.showEmptyProgress(true)
            loadPage(firstPage)
        }

        override fun refresh() {
            Timber.d("refresh")
            currentState = RefreshState()

            viewController.showRefreshProgress(true)
            loadPage(firstPage)
        }

        override fun release() {
            currentState = RELEASED()
            requestDisposable?.dispose()
        }
    }

    /**
     * Состояние сброса состояния пагинации.
     */
    private inner class RELEASED : State<T>
}