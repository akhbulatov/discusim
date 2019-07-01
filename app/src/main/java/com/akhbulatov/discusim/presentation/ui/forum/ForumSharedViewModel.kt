package com.akhbulatov.discusim.presentation.ui.forum

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.domain.global.models.Forum

class ForumSharedViewModel : ViewModel() {
    val forum = MutableLiveData<Forum>()
}