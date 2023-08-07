package com.shakiv.husain.instagramui.presentation.home

import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.data.mapper.toPost
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.model.Post
import com.shakiv.husain.instagramui.presentation.common.composable.PostActions
import com.shakiv.husain.instagramui.presentation.common.composable.ProfileImage
import com.shakiv.husain.instagramui.utils.DateUtils
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils

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
            userProfile = "IconsInstagram.ProfilePic"
        ),
        postActions = com.shakiv.husain.instagramui.data.model.PostActions(false, false)
    )
    FeedListItem(post = post.toPost(), onItemClick = {})
}

@Composable
fun FeedListItem(
    post: Post,
    modifier: Modifier = Modifier,
    onItemClick: (Post) -> Unit,
) {

    Log.d("TAGPostList", "PostList: $post")

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

                ProfileImage(profilePath = 0) // TODO : Change it to the Actual Path

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
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = DateUtils.toDuration(post.date.orEmpty()),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                ) {

                    ImageUtils.setImage(
                        imageId = IconsInstagram.IC_MoreOption,
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

            if (post.imageUrl.isNotEmpty()) {
                ImageUtils.setImage(
                    imageId = IconsInstagram.IcMyProfile,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }

            val onLikeClick = {
            }

            val onCommentClicked = {
            }

            val onShareClicked = {
            }

            PostActions(
                isLiked = post.isLiked ?: true,
                onLikeClicked = onLikeClick,
                onCommentClicked = onCommentClicked,
                onShareClicked = onShareClicked
            )

        }
    }
}