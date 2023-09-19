package com.shakiv.husain.contentvibe.data

import com.shakiv.husain.contentvibe.presentation.common.composable.BottomSheetItem

object MockService {
    fun getBottomSheetItems(): List<BottomSheetItem> {
        return listOf(
            BottomSheetItem.HIDE,
            BottomSheetItem.REPORT,
            BottomSheetItem.DELETE
        )
    }
}