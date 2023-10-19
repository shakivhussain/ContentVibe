package com.shakiv.husain.contentvibe.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.contentvibe.domain.model.Post
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.IconsContentVibe.IcBookmark
import com.shakiv.husain.contentvibe.utils.IconsContentVibe.IcChat
import com.shakiv.husain.contentvibe.utils.IconsContentVibe.IcLikeBorder
import com.shakiv.husain.contentvibe.utils.IconsContentVibe.IcLikeFilled
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.MultipleEventsCutterUtils.multipleEventsCutter
import com.shakiv.husain.contentvibe.utils.extentions.logd


@Preview
@Composable
fun PrevPostAction() {
//    PostActions(
//        modifier = Modifier,
//        onLikeClicked = { /*TODO*/ },
//        onCommentClicked = { /*TODO*/ },
//        onShareClicked = {}
//
//    ) {
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
                 if (it) IcLikeFilled else IcLikeBorder
            }?: IcLikeBorder


            Text(text = "${post.likes}")



            multipleEventsCutter { manager ->

                IconButton(onClick = {

                    manager.processEvent {
                        onLikeClicked()
                        logd(" Like Clicked ")
                    }

                }) {
                    ImageUtils.setImage(
                        modifier= Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        imageVector = likeIcon
                    )
                }

            }


//            IconButton(onClick = { onCommentClicked() }) {
//
//                ImageUtils.setImage(
//                    modifier= Modifier.size(24.dp),
//                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
//                    imageId = IcChat
//                )
//            }
//
//            IconButton(onClick = { onShareClicked() }) {
//                ImageUtils.setImage(
//                    modifier= Modifier.size(24.dp),
//                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
//                    imageId = IconsContentVibe.IcSharePost
//                )
//            }
        }

//        IconButton(onClick = { onCommentClicked() }) {
//
//            ImageUtils.setImage(imageId = IcBookmark)
//        }

    }


}