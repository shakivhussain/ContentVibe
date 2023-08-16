package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shakiv.husain.instagramui.data.repository.PhotoSaverRepositoryImp
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGrid(
    modifier: Modifier,
    photos: List<File>,
    onRemove: ((photo: File, index: Int) -> Unit)? = null
) {

    Row(modifier) {
        repeat(PhotoSaverRepositoryImp.MAX_LOG_PHOTOS_LIMIT) { index ->
            val file = photos.getOrNull(index)

            if (file == null) {
                Box(Modifier.weight(1f))
            } else {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .aspectRatio(1f)
                ) {
                    AsyncImage(
                        model = file,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    if (onRemove != null) {
                        FilledTonalIconButton(onClick = { onRemove(file, index) }) {
                            Icon(Icons.Filled.Close, null)
                        }
                    }
                }
            }
            Spacer(Modifier.width(8.dp))
        }
    }
}