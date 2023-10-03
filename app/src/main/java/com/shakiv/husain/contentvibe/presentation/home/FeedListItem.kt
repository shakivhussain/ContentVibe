package com.shakiv.husain.contentvibe.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shakiv.husain.contentvibe.data.mapper.toPost
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.Post
import com.shakiv.husain.contentvibe.presentation.common.composable.PostActions
import com.shakiv.husain.contentvibe.utils.DateUtils
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.extentions.logd

@Preview
@Composable
fun PreviewPostItem(
) {
    val post = PostEntity(
        id = "jhdgdr8734h3j4j3",
        post = "Top things to know in Android Platform and Quality at Google I/O '23",
        isLiked = true,
        user = UserEntity(
            "Shakiv Husain", isAnonymous = true, "Developer",
            profileUrl = "contentvibe.ProfilePic"
        ),
        postActions = com.shakiv.husain.contentvibe.data.model.PostActions(false, false),
        images = "https://firebasestorage.googleapis.com/v0/b/contentvibe-f9adc.appspot.com/o/images%2F4e44cc83-9ee7-4d9b-9b81-b3e022adabd1.jpg?alt=media&token=02ddcf9b-e66a-41d2-bdd4-55ad660b6cf9"
    )
    FeedListItem(post = post.toPost(), onItemClick = {},
        onLikeClick = {},
        onMoreOptionClick = {},
        onCommentClicked = {},
        onProfileClick = {},
        onShareClicked = {}
    )
}

@Composable
fun FeedListItem(
    post: Post,
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit,
    onMoreOptionClick: (Post) -> Unit,
    onItemClick: (Post) -> Unit,
    onCommentClicked: () -> Unit,
    onProfileClick : (Post) -> Unit,
    onShareClicked: () -> Unit,
) {

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable { onItemClick(post) },
        colors = CardDefaults
            .cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

//                ProfileImage(profilePath = 0) // TODO : Change it to the Actual Path


                ImageUtils.SetProfileImage(
                    post.userProfile.orEmpty(),
                    modifier= Modifier.size(40.dp),
                    onProfileClick = { onProfileClick(post) }
                    )

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    modifier = Modifier.weight(1F),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = post.userName.orEmpty(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = post.userAbout.orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }


                }

                IconButton(
                    onClick = { onMoreOptionClick(post) },
                ) {

                    ImageUtils.setImage(
                        imageId = IconsContentVibe.IC_MoreOption,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.post.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(12.dp)
            )

            if (!post.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = post.imageUrl.orEmpty(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 12.dp,
                                topStart = 12.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp

                            )
                        )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }


//            if(post.imageUrl.isNullOrEmpty()){
//                Spacer(
//                    modifier = Modifier.width(8.dp)
//                )
//            }

            Text(
                modifier = Modifier.padding(12.dp),
                text = DateUtils.toDuration(post.date.orEmpty()),
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis
            )




            PostActions(
                post = post,
                onLikeClicked = onLikeClick,
                onCommentClicked = onCommentClicked,
                onShareClicked = onShareClicked
            )

        }
    }
}