package com.akhbulatov.discusim.presentation.channeldetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.presentation.ui.users.UserType
import com.akhbulatov.discusim.presentation.ui.users.UsersFragment

class ChannelDetailsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = PAGE_TITLES[position]

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> UsersFragment.newInstance("channel-gifs", UserType.MODERATOR) // TODO
            else -> throw IllegalArgumentException() // TODO
        }

    companion object {
        const val PAGE_COUNT = 1
        val PAGE_TITLES = arrayListOf("Mods") // TODO
    }
}