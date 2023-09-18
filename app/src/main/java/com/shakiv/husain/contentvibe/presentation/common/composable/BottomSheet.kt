package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils


@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun PreviewBottomSheet() {
    MoreOptionBottomSheet(
        onItemClick = {}, onDismissListener = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionBottomSheet(
    onItemClick: (BottomSheetItem) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissListener: () -> Unit,
    itemsLists: List<BottomSheetItem> = emptyList()
) {
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = {
            onDismissListener()
        },
        shape = RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp
        ),
    ) {
        BottomSheetContent(onItemClick, itemsLists = itemsLists)
    }

}


@Composable
fun BottomSheetContent(
    onItemClick: (BottomSheetItem) -> Unit,
    itemsLists: List<BottomSheetItem> = emptyList()
) {
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp)
    ) {
        items(itemsLists) {
            BottomSheetItem(it, onItemClick = onItemClick)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetItem(
    bottomSheetItem: BottomSheetItem,
    onItemClick: (BottomSheetItem) -> Unit
) {

    Card(
        onClick = { onItemClick(bottomSheetItem) },
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            bottomSheetItem.image.let { icon ->
                ImageUtils.setImage(
                    imageId = icon,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = stringResource(id = bottomSheetItem.title),
                style = MaterialTheme.typography.labelMedium,
            )
        }

    }
}


enum class BottomSheetItem(@StringRes val title: Int, val image: Int) {
    HIDE(title = R.string.app_name, IconsContentVibe.CHAT),
    REPORT(title = R.string.app_name, IconsContentVibe.CHAT),
    DELETE(title = R.string.app_name, IconsContentVibe.CHAT),
}



