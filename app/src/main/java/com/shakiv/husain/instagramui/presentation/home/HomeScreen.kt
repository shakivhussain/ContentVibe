package com.shakiv.husain.instagramui.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.model.PostActions
import com.shakiv.husain.instagramui.data.model.PostEntity
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.model.Post
import com.shakiv.husain.instagramui.domain.model.UserPreferences
import com.shakiv.husain.instagramui.presentation.app.DataStoreViewModel
import com.shakiv.husain.instagramui.presentation.common.composable.ProfileImage
import com.shakiv.husain.instagramui.utils.DataStoreConstant
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.extentions.logd
import kotlinx.coroutines.delay

@Composable
fun HomeFeed(
    onItemClick: (Post) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    ) {

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeFeed(
        uiState = uiState,
        onItemClick = {
        },
        onLiked = {
            logd( "HomeFeed onLiked Cliked : ${it} ")

//            return@HomeFeed
            homeViewModel.onPostLiked(it)
        }
    )

}

@Composable
fun HomeFeed(uiState: HomeViewModelState, onItemClick: (Post) -> Unit, onLiked: (Post) -> Unit, homeViewModel: HomeViewModel= hiltViewModel()) {

    val postLazyListState = rememberLazyListState()
    val storyLazyListState = rememberLazyListState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PostList(
            postList = uiState.posts,
            storyList = uiState.stories,
            postLazyListState = postLazyListState,
            storyLazyListState,
            onItemClick = onItemClick,
            onLiked = { onLiked(it) }
        )
    }

}


@Composable
fun PostList(
    postList: List<Post>,
    storyList: List<StoryItem>,
    postLazyListState: LazyListState,
    storyLazyListState: LazyListState,
    onItemClick: (Post) -> Unit,
    onLiked: (Post) -> Unit
) {



    Column(modifier = Modifier.fillMaxSize()) {

        logd("HomeFeed Data Update")

        AppHeader()

        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = postLazyListState
        ) {
            item {
                StoryList(storyList = storyList, storyLazyListState)
//                Spacer(modifier = Modifier.height(18.dp))
                Divider(
                    Modifier.fillMaxWidth(), thickness = .2.dp,
                )
            }
            items(postList) { post ->

                FeedListItem(
                    onLikeClick = { onLiked(post) }
                    ,post = post
                ) { postItem ->
                    onItemClick(postItem)
                }
            }
        }
    }


}

@Composable
fun AppHeader() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Text(
            text = "Instagram", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = IconsInstagram.LIKE),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
            )
        }


        IconButton(onClick = { /*TODO*/ }) {
            Image(

                painter = painterResource(id = IconsInstagram.CHAT),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
            )

        }

    }
}


@Composable
fun StoryList(storyList: List<StoryItem>, storyLazyListState: LazyListState) {
    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = storyLazyListState
    ) {
        items(storyList) { story ->
            StoryListItem(storyItem = story)
        }
    }
}

@Composable
fun StoryListItem(storyItem: StoryItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileImage(profilePath = storyItem.storyImage, Modifier.size(70.dp))
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = storyItem.userName,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(2.dp)
                .width(70.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun PreviewStoryListItem() {
    val user = UserEntity(
        "$1 Shakiv Husain", isAnonymous = true, "Professional",
        userProfile = "IconsInstagram.ProfilePic"
    )

    val postAction = PostActions(
        isLiked = 1 % 2 == 0,
        isDislike = 2 % 2 != 0,
    )

    val post = PostEntity(
        "Shakiv Husain",
        user = user,
        postActions = postAction
    )

//    FeedListItem(
//        post.toPost(),
////        onLikeClick = {}
//    ) {
//
//    }
}
