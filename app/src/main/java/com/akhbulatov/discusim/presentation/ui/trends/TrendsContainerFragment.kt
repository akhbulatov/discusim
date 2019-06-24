package com.akhbulatov.discusim.presentation.ui.trends

import android.os.Bundle
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadType
import javax.inject.Inject

class TrendsContainerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_trends

    @Inject lateinit var router: FlowRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setChildScreen()
    }

    private fun setChildScreen() {
        val threadsScreen = Screens.Threads(threadType = ThreadType.TRENDS)
        childFragmentManager.beginTransaction()
            .replace(R.id.threadsContainer, threadsScreen.fragment, threadsScreen.screenKey)
            .commit()
    }

    override fun onBackPressed() = router.exit()
}