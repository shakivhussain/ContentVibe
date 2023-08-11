package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit)? = null,
    popUpScreen: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            IconButton(onClick = { popUpScreen() }) {
                ImageUtils.setImage(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(28.dp),
                    imageId = IconsInstagram.IcBack,
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = { actions?.invoke(this) }
    )

}