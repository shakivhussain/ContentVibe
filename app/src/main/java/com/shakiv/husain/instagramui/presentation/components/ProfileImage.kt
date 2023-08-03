package com.shakiv.husain.instagramui.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun ProfileImage(profilePath: Int, modifier: Modifier= Modifier) {
    Image(
        painter = painterResource(id = profilePath),
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillWidth
    )
}