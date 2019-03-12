package com.akhbulatov.discusim.presentation.ui.threads

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.global.widgets.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_threads.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.Threads::class)
class ThreadsFragment : BaseFragment() {
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ThreadsViewModel
    private val threadsAdapter = ThreadsAdapter()

    override val layoutRes: Int = R.layout.fragment_threads

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.Threads>(this)
        val forumId = screen.forumId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ThreadsViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        threadsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = threadsAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.threads.observe(this, Observer { showThreads(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showThreads(threads: List<Thread>) {
        threadsAdapter.submitList(threads)
    }

    private fun showContentBlock(show: Boolean) {
        threadsRecyclerView.isVisible = show
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