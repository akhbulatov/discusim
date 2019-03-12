package com.akhbulatov.discusim.presentation.ui.forums

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
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_forums.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.Forums::class)
class ForumsFragment : BaseFragment() {
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ForumsViewModel
    private val forumsAdapter = ForumsAdapter()

    override val layoutRes: Int = R.layout.fragment_forums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.Forums>(this)
        val userId = screen.userId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumsViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forumsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = forumsAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.forums.observe(this, Observer { showForums(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showForums(forums: List<Forum>) {
        forumsAdapter.submitList(forums)
    }

    private fun showContentBlock(show: Boolean) {
        forumsRecyclerView.isVisible = show
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