package com.shakiv.husain.contentvibe.data

import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem

object MockService {
    fun getBottomSheetItems(): List<BottomSheetItem> {
        return listOf(
            BottomSheetItem.HIDE,
            BottomSheetItem.REPORT,
            BottomSheetItem.DELETE
        )
    }
}