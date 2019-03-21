package com.akhbulatov.discusim.presentation.global.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val subscriptions = CompositeDisposable()

    override fun onCleared() {
        subscriptions.clear()
        super.onCleared()
    }

    abstract fun onBackPressed()
}