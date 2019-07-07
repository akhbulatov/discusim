package com.akhbulatov.discusim.presentation.ui.user

import android.os.Bundle
import androidx.core.os.bundleOf
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class UserFlowFragment : FlowFragment() {
    private var userId: Long = 0

    override fun getLaunchScreen(): SupportAppScreen = Screens.UserHost(userId)

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = requireNotNull(arguments?.getLong(ARG_USER_ID))
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserFlowFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}