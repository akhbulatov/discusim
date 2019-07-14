package com.akhbulatov.discusim.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_host.*
import org.jetbrains.anko.support.v4.share
import javax.inject.Inject

class UserHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_host

    @Inject lateinit var router: FlowRouter
    private val userSharedViewModel: UserSharedViewModel by viewModels()

    private var userId: Long = 0
    private val userPagerAdapter by lazy { UserPagerAdapter() }
    private var sharedUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = requireNotNull(arguments?.getLong(ARG_USER_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        userPager.adapter = userPagerAdapter
        userTabLayout.setupWithViewPager(userPager)
        observeUiChanges()
    }

    private fun setupToolbar() {
        with(toolbar) {
            inflateMenu(R.menu.user_host)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                sharedUser?.let { share(it.url) }
                true
            }
        }
    }

    private fun observeUiChanges() {
        userSharedViewModel.user.observe(this, Observer {
            this.sharedUser = it
            toolbar.title = it.username
        })
    }

    private inner class UserPagerAdapter :
        FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.user_host_details_tab)
            1 -> getString(R.string.user_host_activity_tab)
            2 -> getString(R.string.user_host_comments_tab)
            3 -> getString(R.string.user_host_forums_tab)
            4 -> getString(R.string.user_host_followers_tab)
            5 -> getString(R.string.user_host_following_tab)
            else -> throw IllegalArgumentException()
        }

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.UserDetails(userId).fragment
            1 -> Screens.UserActivity(userId).fragment
            2 -> Screens.UserComments(userId).fragment
            3 -> Screens.UserForums(userId).fragment
            4 -> Screens.UserFollowers(userId).fragment
            5 -> Screens.UserFollowing(userId).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getCount(): Int = 6
    }

    override fun onBackPressed() = router.exit()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserHostFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}