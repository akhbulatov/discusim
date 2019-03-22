package com.akhbulatov.discusim.presentation.ui.channel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment

class ChannelPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = PAGE_TITLES[position]

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> ThreadsFragment.newInstance("channel-gifs") // TODO
            else -> throw IllegalArgumentException() // TODO
        }

    companion object {
        const val PAGE_COUNT = 1
        val PAGE_TITLES = arrayListOf("Latest") // TODO
    }
}