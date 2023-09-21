package com.shakiv.husain.contentvibe.presentation.common.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.contentvibe.data.MockService.getBottomSheetItems
import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem
import com.shakiv.husain.contentvibe.utils.ImageUtils
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun PreviewBottomSheet() {
    MoreOptionBottomSheet(
        itemsLists = getBottomSheetItems(),
        onItemClick = {}, onDismissListener = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Preview CommentBottom Sheet")
@Composable
fun PreviewCommentBottomSheet() {
    CommentBottomSheet(){

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissListener: () -> Unit,
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

        CommentBottomSheetContent(Modifier.padding())

    }

}

@Composable
fun CommentBottomSheetContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        var isLoading by remember { mutableStateOf(true) }

        LaunchedEffect(true) {
            delay(1500)
            isLoading = false
        }

        if (isLoading) {
            ProgressBar(modifier.fillMaxWidth())
        } else {
            Text(
                text = "Screen under construction",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This screen is still under construction.",
                style = MaterialTheme.typography.labelMedium
            )

//            CommentTextField()

        }


    }

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


            val iconAndTextColor =
                if (bottomSheetItem == BottomSheetItem.DELETE) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground


            bottomSheetItem.image.let { icon ->
                ImageUtils.setImage(
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(
                        color = iconAndTextColor
                    )
                )
            }

            Spacer(Modifier.width(8.dp))


            Text(
                text = stringResource(id = bottomSheetItem.title),
                style = MaterialTheme.typography.labelMedium,
                color = iconAndTextColor

            )
        }

    }
}






