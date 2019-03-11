package com.akhbulatov.discusim.presentation.ui.profile.activities

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
import com.akhbulatov.discusim.domain.global.models.Activity
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_profile_activities.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.ProfileActivities::class)
class ProfileActivitiesFragment : BaseFragment() {
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileActivitiesViewModel
    private val activitiesAdapter = ProfileActivitiesAdapter()

    override val layoutRes: Int = R.layout.fragment_profile_activities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.ProfileActivities>(this)
        val userId = screen.userId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfileActivitiesViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activitiesRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = activitiesAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.activities.observe(this, Observer { showActivities(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showActivities(activities: List<Activity>) {
        activitiesAdapter.submitList(activities)
    }

    private fun showContentBlock(show: Boolean) {
        activitiesRecyclerView.isVisible = show
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