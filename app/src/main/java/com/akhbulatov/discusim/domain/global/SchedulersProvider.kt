package com.akhbulatov.discusim.domain.global

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulersProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}

class AppSchedulers @Inject constructor(): SchedulersProvider {
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
}