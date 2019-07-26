package com.akhbulatov.discusim.presentation.ui.about.libraries

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.AppLibrary
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.AppLibraryAdapter
import kotlinx.android.synthetic.main.fragment_app_libraries.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.support.v4.browse
import javax.inject.Inject

class AppLibrariesFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_app_libraries

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AppLibrariesViewModel by viewModels { viewModelFactory }

    private val appLibrariesAdapter by lazy {
        AppLibraryAdapter { browse(it.webUrl) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        run { viewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        with(appLibrariesRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = appLibrariesAdapter
        }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.appLibraries.observe(this, Observer { showAppLibraries(it) })
    }

    private fun showEmptyProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showEmptyError(message: String) {
        errorTextView.text = message
        errorLayout.isVisible = true
    }

    private fun showEmptyData(show: Boolean) {
        dataLayout.isVisible = show
    }

    private fun showAppLibraries(appLibraries: List<AppLibrary>) {
        appLibrariesAdapter.submitList(appLibraries)
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}
