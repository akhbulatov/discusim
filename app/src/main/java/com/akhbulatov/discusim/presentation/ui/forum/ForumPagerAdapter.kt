package com.akhbulatov.discusim.presentation.ui.forum

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.users.UserType
import me.aartikov.alligator.navigationfactories.NavigationFactory
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation

class ForumPagerAdapter(
    fm: FragmentManager,
    private val navigationFactory: NavigationFactory
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? = PAGE_TITLES[position]

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> {
                val screenImpl = navigationFactory.getScreenImplementation(Screens.Threads::class.java)
                (screenImpl as FragmentScreenImplementation).createFragment(Screens.Threads("channel-gifs")) // TODO
            }

            else -> {
                val screenImpl = navigationFactory.getScreenImplementation(Screens.Users::class.java)
                val screen = Screens.Users("itcua", UserType.TOP_COMMENTER) // TODO
                (screenImpl as FragmentScreenImplementation).createFragment(screen)
            }
        }

    companion object {
        const val PAGE_COUNT = 2
        val PAGE_TITLES = arrayListOf("Latest Discussions", "Top Commenters") // TODO
    }
}