package com.akhbulatov.discusim.presentation.ui.global.utils

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(@StringRes resId: Int) {
    Snackbar.make(this.view!!, resId, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(text: String) {
    Snackbar.make(this.view!!, text, Snackbar.LENGTH_SHORT).show()
}

fun FlowRouter.navigateToAction(action: Action) {
    when (action.type) {
        Action.Type.DISCUSSION_VOTE -> {
            action.discussionVote?.let { navigateTo(Screens.DiscussionDetails(it.discussion.id)) }
        }
        Action.Type.COMMENT -> {
            action.comment?.let {
                if (it.forum.isChannel()) {
                    navigateTo(Screens.DiscussionDetails(it.discussion.id))
                } else {
                    navigateTo(Screens.ExternalBrowser(it.discussion.link))
                }
            }
        }
    }
}