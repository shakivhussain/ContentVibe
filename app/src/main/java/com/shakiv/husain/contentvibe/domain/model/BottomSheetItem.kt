package com.shakiv.husain.contentvibe.domain.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.utils.IconsContentVibe

enum class BottomSheetItem(@StringRes val title: Int, val image: ImageVector) {
//    HIDE(title = R.string.hide, IconsContentVibe.IC_HIDE),
    REPORT(title = R.string.report, IconsContentVibe.IC_REPORT),
    DELETE(title = R.string.delete, IconsContentVibe.IC_DELETE),
    LOGOUT(title = R.string.logout, IconsContentVibe.IC_DELETE),
    DISMISS(title = R.string.dismiss, IconsContentVibe.IC_HIDE),
}