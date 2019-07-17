package com.akhbulatov.discusim.presentation.ui.forum.discussions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.domain.global.models.Discussion

class DiscussionSharedViewModel : ViewModel() {
    val discussion = MutableLiveData<Discussion>()
}