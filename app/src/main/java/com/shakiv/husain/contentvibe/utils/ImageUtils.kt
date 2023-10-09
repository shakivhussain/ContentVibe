package com.shakiv.husain.contentvibe.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SetProfileImage(
        imagePath: Any,
        modifier: Modifier = Modifier,
        onProfileClick: () -> Unit
    ) {
        var isLoading by remember { mutableStateOf(true) }

        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(
                pressedElevation = 0.dp,
                defaultElevation = 0.dp
            ),
            onClick = onProfileClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
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

                    onError = {
                        isLoading = false
                    },
                    error = painterResource(id = R.drawable.ic_media),
                    contentDescription = stringResource(R.string.image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )

            }
        }


    }

    @Composable
    fun SetImage(
        modifier: Modifier = Modifier,
        imagePath: Any,
        contentScale: ContentScale = ContentScale.Crop,
        showLoading : Boolean = true,
        onLoading : () -> Unit = {},
        onSuccess : () -> Unit = {},
    ) {

        var isLoading by remember { mutableStateOf(true) }

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            if (isLoading && showLoading) {
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
                    onSuccess()
                },
//                placeholder = painterResource(id = IconsContentVibe.IC_MEDIA) ,
                contentDescription = stringResource(R.string.image),
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp))
            )

        }

    }

}