package com.shakiv.husain.instagramui.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shakiv.husain.instagramui.domain.model.Post
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcBookmark
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcChat
import com.shakiv.husain.instagramui.utils.IconsInstagram.IcLike
import com.shakiv.husain.instagramui.utils.ImageUtils


@Preview
@Composable
fun PrevPostAction() {
//    PostActions(
//        modifier = Modifier.padding(), onLikeClicked = { /*TODO*/ },
//        onCommentClicked = { /*TODO*/ }) {
//    }
}

@Composable
fun PostActions(
    modifier: Modifier = Modifier,
    post: Post ,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            val likeIcon = post.isLiked?.let {
                 if (it) IcLike else IcChat
            }?: IcLike

            IconButton(onClick = { onLikeClicked() }) {
                ImageUtils.setImage(imageId = likeIcon)
            }

            IconButton(onClick = { onCommentClicked() }) {

                ImageUtils.setImage(imageId = IcChat)
            }

            IconButton(onClick = { onShareClicked() }) {
                ImageUtils.setImage(imageId = IconsInstagram.IcSharePost)
            }
        }

        IconButton(onClick = { onCommentClicked() }) {

            ImageUtils.setImage(imageId = IcBookmark)
        }

    }


}