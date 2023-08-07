package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcBookmark
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcChat
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcLike
import com.shakiv.husain.instagramui.utils.ImageUtils


@Preview
@Composable
fun PrevPostAction() {
    PostActions(
        modifier = Modifier.padding(), onLikeClicked = { /*TODO*/ },
        onCommentClicked = { /*TODO*/ }) {

    }
}

@Composable
fun PostActions(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {

    val modifier = Modifier.padding(4.dp)


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            val likeIcon = if (isLiked) IcLike else IcLike

            IconButton(
                onClick = {
                    onLikeClicked()
                },
                modifier = Modifier
                    .padding(0.dp),
            ) {
                ImageUtils.setImage(imageId = likeIcon)
            }


            IconButton(
                onClick = { onCommentClicked() },
                modifier = Modifier
                    .padding(0.dp),
            ) {

                ImageUtils.setImage(imageId = IcChat)
            }


            IconButton(
                onClick = { onShareClicked() },
                modifier = Modifier
                    .padding(0.dp),
            ) {
                ImageUtils.setImage(imageId = IconsInstagram.IcSharePost)
            }
        }

        IconButton(
            onClick = { onCommentClicked() },
            modifier = Modifier
                .padding(end = 0.dp),
        ) {

            ImageUtils.setImage(imageId = IcBookmark)
        }

    }


}