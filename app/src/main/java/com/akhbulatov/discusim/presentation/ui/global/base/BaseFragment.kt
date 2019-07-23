package com.akhbulatov.discusim.presentation.ui.global.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    open fun onBackPressed() {}
}
