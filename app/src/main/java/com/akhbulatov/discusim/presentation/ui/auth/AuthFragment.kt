package com.akhbulatov.discusim.presentation.ui.auth

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_auth

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AuthViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        observeUiChanges()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        with(authWebView) {
            settings.javaScriptEnabled = true
            loadUrl(viewModel.getAuthorizeUrl())
            webViewClient = object : WebViewClient() {
                @Suppress("OverridingDeprecatedMember")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    return viewModel.isRedirectToLogin(url)
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                    return viewModel.isRedirectToLogin(request.url.toString())
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showProgress(true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    showProgress(false)
                }
            }
        }
    }

    private fun observeUiChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it) })
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(message: String) {
        showSnackbar(message)
    }

    override fun onBackPressed() {
        if (authWebView.canGoBack()) {
            authWebView.goBack()
        } else {
            viewModel.onBackPressed()
        }
    }
}