package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview()
@Composable
fun PreviewBottomSheet() {
    MoreOptionBottomSheet()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionBottomSheet() {


    ModalBottomSheet(modifier = Modifier, sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false), onDismissRequest = {
    }, shape = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 10.dp
    ),
    ) {
        BottomSheetItem()
    }

}


@Composable
fun BottomSheetItem() {


    Card {

    }
    Text(text = "Mute")
}





