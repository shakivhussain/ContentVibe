package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.utils.AppUtils
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils


@Composable
fun ProfileImage(profilePath: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = IconsContentVibe.ProfilePic),
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillWidth
    )
}


@Preview
@Composable
fun ImageRainbowBorder(imageUrl : String = "",modifier: Modifier = Modifier) {

    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            AppUtils.rainbowColors
        )
    }
    val borderWidth = 2.dp


    ImageUtils.setImage(
        imageId = R.drawable.profile_pic,
        contentDescription = stringResource(id = R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(40.dp)
            .border(
                BorderStroke(borderWidth, rainbowColorsBrush),
                CircleShape
            )
            .padding(borderWidth)
            .clip(CircleShape),
    )

}

