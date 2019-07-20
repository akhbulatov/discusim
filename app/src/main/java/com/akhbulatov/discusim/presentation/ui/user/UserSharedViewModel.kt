package com.akhbulatov.discusim.presentation.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.domain.global.models.UserDetails

class UserSharedViewModel : ViewModel() {
    val user = MutableLiveData<UserDetails>()
}