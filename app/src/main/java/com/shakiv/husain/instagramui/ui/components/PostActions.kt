package com.shakiv.husain.instagramui.ui.components

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
import com.shakiv.husain.instagramui.R
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
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        val likeIcon = if (isLiked) R.drawable.ic_message else R.drawable.ic_message


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
            ImageUtils.setImage(imageId = R.drawable.ic_chat)
        }


        IconButton(
            onClick = { onShareClicked() },
            modifier = Modifier
                .padding(0.dp),
        ) {
            ImageUtils.setImage(imageId = R.drawable.ic_account)
        }
    }

}