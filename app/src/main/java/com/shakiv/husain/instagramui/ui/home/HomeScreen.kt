package com.shakiv.husain.instagramui.ui.home

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.data.LocalPostProvider
import com.shakiv.husain.instagramui.data.PostItem
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.ui.components.ProfileImage
import com.shakiv.husain.instagramui.utils.IconsInstagram

@Composable
fun HomeFeed(
    onItemClick: (PostItem) -> Unit,
) {

    val postList = remember { mutableStateOf(LocalPostProvider.allUserPost()) }
    val storyList = remember { mutableStateOf(LocalPostProvider.allStory()) }

    val postLazyListState = rememberLazyListState()
    val storyLazyListState = rememberLazyListState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        PostList(
            postList = postList.value, storyList = storyList.value,
            postLazyListState = postLazyListState,
            storyLazyListState
        ) {
            onItemClick(it)
        }
    }
}

@Composable
fun PostList(
    postList: List<PostItem>,
    storyList: List<StoryItem>,
    postLazyListState: LazyListState,
    storyLazyListState: LazyListState,
    onItemClick: (PostItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = postLazyListState
    ) {


        item {
            AppHeader()
        }

        item {
            StoryList(storyList = storyList, storyLazyListState)
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                Modifier.fillMaxWidth(), thickness = .5.dp
            )
        }
        items(postList) { post ->
            FeedListItem(postItem = post) { postItem ->
                onItemClick(postItem)
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
            .padding(horizontal = 8.dp)
    ) {

        Text(
            text = "Instagram", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        Image(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(28.dp),
            imageVector = IconsInstagram.HEART,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
        )
        Image(
            modifier = Modifier.size(28.dp),
            imageVector = IconsInstagram.EMAIL,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
        )
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
    val storyItem = StoryItem("Shakiv Husain", storyImage = IconsInstagram.ProfilePic)
    StoryListItem(storyItem)
}
