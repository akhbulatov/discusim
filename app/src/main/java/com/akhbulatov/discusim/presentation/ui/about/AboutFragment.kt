package com.akhbulatov.discusim.presentation.ui.about

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.email
import org.jetbrains.anko.support.v4.share
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
        privacyPolicyTextView.setOnClickListener(this)
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
            R.id.writeFeedbackTextView -> email(APP_FEEDBACK_EMAIL)
            R.id.rateAppTextView -> browse(APP_PLAY_MARKET_URL)
            R.id.shareAppTextView -> share(APP_PLAY_WEB_URL)
            R.id.librariesTextView -> viewModel.onLibrariesClicked()
            R.id.privacyPolicyTextView -> browse(PRIVACY_POLICY_URL)
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        const val APP_FEEDBACK_EMAIL = "feedback.discusim@gmail.com"
        const val APP_PLAY_MARKET_URL = "market://details?id=${BuildConfig.APPLICATION_ID}"
        const val APP_PLAY_WEB_URL = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        const val PRIVACY_POLICY_URL = "https://discusim.flycricket.io/privacy.html"
    }
}
