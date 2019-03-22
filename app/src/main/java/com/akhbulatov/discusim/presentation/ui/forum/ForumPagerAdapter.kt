package com.akhbulatov.discusim.presentation.ui.forum

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.users.UserType
import com.akhbulatov.discusim.presentation.ui.users.UsersFragment

class ForumPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = PAGE_TITLES[position]

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> ThreadsFragment.newInstance("channel-gifs") // TODO
            else -> UsersFragment.newInstance("itcua", UserType.TOP_COMMENTER) // TODO
        }

    companion object {
        const val PAGE_COUNT = 2
        val PAGE_TITLES = arrayListOf("Latest Discussions", "Top Commenters") // TODO
    }
}