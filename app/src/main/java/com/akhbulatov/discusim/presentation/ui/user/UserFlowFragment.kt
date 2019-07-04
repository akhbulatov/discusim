package com.akhbulatov.discusim.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import kotlinx.android.synthetic.main.fragment_user_flow.*

class UserFlowFragment : FlowFragment() {
    override val layoutRes: Int = R.layout.fragment_user_flow

    private var userId: Long = 0
    private val userPagerAdapter by lazy { UserPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = requireNotNull(arguments?.getLong(ARG_USER_ID))

        val userSharedViewModel = ViewModelProviders.of(this)[UserSharedViewModel::class.java]
        userSharedViewModel.user.observe(this, Observer {
            toolbar.title = it.username
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        userPager.adapter = userPagerAdapter
        userTabLayout.setupWithViewPager(userPager)
    }

    private inner class UserPagerAdapter :
        FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.user_flow_details_tab)
            else -> throw IllegalArgumentException()
        }

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.UserDetails(userId).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getCount(): Int = 1 // TODO
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserFlowFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}