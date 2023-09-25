package com.shakiv.husain.contentvibe.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.presentation.common.composable.ProgressBar
import com.shakiv.husain.contentvibe.utils.extentions.getContext

object ImageUtils {

    @Composable
    fun setImage(
        imageVector: ImageVector,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
        contentDescription: String? = null,
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
        contentDescription: String? = null,
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

    @Composable
    fun SetProfileImage(
        imagePath: Any,
        modifier: Modifier = Modifier
    ) {

        var isLoading by remember { mutableStateOf(true) }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            if (isLoading) {
                ProgressBar()
            }

            AsyncImage(
                model = ImageRequest.Builder(getContext())
                    .data(imagePath)
                    .crossfade(true)
                    .build(),
                onLoading = {
                    isLoading = true
                },
                onSuccess = {
                    isLoading = false
                },
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )

        }

    }

    @Composable
    fun SetImage(
        imagePath: Any,
        modifier: Modifier = Modifier
    ) {

        var isLoading by remember { mutableStateOf(true) }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            if (isLoading) {
                ProgressBar()
            }

            AsyncImage(
                model = ImageRequest.Builder(getContext())
                    .data(imagePath)
                    .crossfade(true)
                    .build(),
                onLoading = {
                    isLoading = true
                },
                onSuccess = {
                    isLoading = false
                },
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp))
            )

        }

    }

}