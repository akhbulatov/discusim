package com.akhbulatov.discusim.presentation.ui.channeldetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.users.UserType
import me.aartikov.alligator.navigationfactories.NavigationFactory
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation

class ChannelDetailsPagerAdapter(
    fm: FragmentManager,
    private val navigationFactory: NavigationFactory
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = PAGE_TITLES[position]

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> {
                val screenImpl = navigationFactory.getScreenImplementation(Screens.Users::class.java)
                val screen = Screens.Users("channel-gifs", UserType.MODERATOR) // TODO
                (screenImpl as FragmentScreenImplementation).createFragment(screen)
            }

            else -> throw IllegalArgumentException() // TODO
        }

    companion object {
        const val PAGE_COUNT = 1
        val PAGE_TITLES = arrayListOf("Mods") // TODO
    }
}