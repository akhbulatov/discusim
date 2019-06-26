package com.akhbulatov.discusim.domain.global.eventbus

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * Работает как шина событий (event bus) для одного ресурса.
 *
 * Смотрите [здесь](https://proandroiddev.com/state-propagation-in-android-with-rxjava-subjects-81db49a0dd8e)
 */
open class Store<T> {
    private val storeSubject: Relay<T> = BehaviorRelay.create<T>().toSerialized()

    fun observe(): Observable<T> =
        storeSubject.hide()
            .distinctUntilChanged()

    fun publish(value: T) = storeSubject.accept(value)
}