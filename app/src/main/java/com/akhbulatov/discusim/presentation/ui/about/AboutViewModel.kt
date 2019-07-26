package com.akhbulatov.discusim.presentation.ui.about

import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.FlowRouter
import javax.inject.Inject

class AboutViewModel @Inject constructor(
    private val router: FlowRouter
) : BaseViewModel() {

    override fun onBackPressed() = router.exit()
}
