package com.akhbulatov.discusim.presentation.ui.about

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*
import javax.inject.Inject

class AboutFragment : BaseFragment(), View.OnClickListener {
    override val layoutRes: Int = R.layout.fragment_about

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AboutViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        run { viewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        writeFeedbackTextView.setOnClickListener(this)
        rateAppTextView.setOnClickListener(this)
        shareAppTextView.setOnClickListener(this)
        librariesTextView.setOnClickListener(this)
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.appInfo.observe(this, Observer { showAppInfo(it) })
    }

    private fun showAppInfo(appInfo: AppInfo) {
        with(appVersionTextView) {
            text = getString(R.string.about_app_version, appInfo.version, appInfo.build)
            isVisible = true
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.writeFeedbackTextView -> {
            }
            R.id.rateAppTextView -> {
            }
            R.id.shareAppTextView -> {
            }
            R.id.librariesTextView -> {
            }
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}
