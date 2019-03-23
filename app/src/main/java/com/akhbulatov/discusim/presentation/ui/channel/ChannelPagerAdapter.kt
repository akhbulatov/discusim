package com.akhbulatov.discusim.presentation.ui.channel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.threads.ThreadType

class ChannelPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> context.getString(R.string.channel_latest)
        1 -> context.getString(R.string.channel_hot)
        else -> context.getString(R.string.channel_popular)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> Screens.Threads("channel-gifs").fragment // TODO
            1 -> Screens.Threads("channel-gifs", ThreadType.HOT).fragment // TODO
            else -> Screens.Threads("channel-gifs", ThreadType.POPULAR).fragment // TODO
        }

    companion object {
        const val PAGE_COUNT = 3
    }
}