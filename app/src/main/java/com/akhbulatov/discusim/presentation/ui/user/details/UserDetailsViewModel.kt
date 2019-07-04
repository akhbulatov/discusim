package com.akhbulatov.discusim.presentation.ui.user.details

import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor() : BaseViewModel() {

    private lateinit var userId: String

    fun setUserId(userId: String) {
        this.userId = userId
    }

    override fun onBackPressed() {
        TODO("not implemented")
    }
}