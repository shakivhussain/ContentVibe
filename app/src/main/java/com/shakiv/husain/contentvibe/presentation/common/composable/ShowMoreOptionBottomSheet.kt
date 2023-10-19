package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMoreOptionBottomSheet(
    bottomSheetState: SheetState,
    isBottomSheetVisible: Boolean = false,
    itemsLists: List<BottomSheetItem> = emptyList(),
    onDismiss: () -> Unit,
    onItemClick: (BottomSheetItem) -> Unit,
) {
    if (isBottomSheetVisible) {
        MoreOptionBottomSheet(
            onItemClick = onItemClick,
            sheetState = bottomSheetState,
            onDismissListener = {
                onDismiss()
            },
            itemsLists = itemsLists
        )
    }

    LaunchedEffect(key1 = isBottomSheetVisible) {
        if (isBottomSheetVisible) bottomSheetState.show() else bottomSheetState.hide()
    }

}