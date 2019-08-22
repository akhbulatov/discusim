package com.akhbulatov.discusim.domain.global

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulersProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}

class SchedulersProviderImpl @Inject constructor() : SchedulersProvider {
    override fun computation(): Scheduler = Schedulers.computation()
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
