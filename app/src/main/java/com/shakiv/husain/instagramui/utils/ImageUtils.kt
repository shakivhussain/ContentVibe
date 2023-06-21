package com.shakiv.husain.instagramui.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

object ImageUtils {

    @Composable
    fun setImage(
        imageVector: ImageVector,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentDescription: String?=null,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Image(
            imageVector = imageVector,
            alignment = alignment,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

    @Composable
    fun setImage(
        @DrawableRes imageId: Int,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentDescription: String?=null,
        contentScale: ContentScale = ContentScale.Fit,
        alpha: Float = DefaultAlpha,
        colorFilter: ColorFilter? = null
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

}