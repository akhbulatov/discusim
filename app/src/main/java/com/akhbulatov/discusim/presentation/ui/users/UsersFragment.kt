package com.akhbulatov.discusim.presentation.ui.users

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_users.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.Users::class)
class UsersFragment : BaseFragment() {
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UsersViewModel
    private val usersAdapter = UsersAdapter()

    override val layoutRes: Int = R.layout.fragment_users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.Users>(this)
        val itemId = screen.itemId
        val userType = screen.type

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UsersViewModel::class.java]
        viewModel.setInitialData(itemId, userType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = usersAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.users.observe(this, Observer { showUsers(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showUsers(users: List<User>) {
        usersAdapter.submitList(users)
    }

    private fun showContentBlock(show: Boolean) {
        usersRecyclerView.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        contentProgressLayout.isVisible = show
    }

    private fun showError(message: String) {
        contentErrorLayout.isVisible = true
        contentErrorTextView.text = message
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}