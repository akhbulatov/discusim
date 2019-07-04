package com.akhbulatov.discusim.presentation.ui.user.details

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.user.UserSharedViewModel
import javax.inject.Inject

class UserDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_details

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var userSharedViewModel: UserSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getString(ARG_USER_ID))

        viewModel = ViewModelProviders.of(this)[UserDetailsViewModel::class.java]
        viewModel.setUserId(userId)

        userSharedViewModel = ViewModelProviders.of(parentFragment!!)[UserSharedViewModel::class.java]
    }

    override fun onBackPressed() {
        TODO("not implemented")
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: String) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}