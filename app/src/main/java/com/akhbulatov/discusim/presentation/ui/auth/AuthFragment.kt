package com.akhbulatov.discusim.presentation.ui.auth

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_auth.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class AuthFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AuthViewModel

    override val layoutRes: Int = R.layout.fragment_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AuthViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        observeChanges()
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

    private fun observeChanges() {
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showProgress(show: Boolean) {
        contentProgressLayout.isVisible = show
    }

    private fun showError(message: String) {
        toast(message)
    }

    override fun onBackPressed() {
        if (authWebView.canGoBack()) {
            authWebView.goBack()
        } else {
            viewModel.onBackPressed()
        }
    }
}