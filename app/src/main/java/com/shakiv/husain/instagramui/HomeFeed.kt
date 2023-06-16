package com.shakiv.husain.instagramui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shakiv.husain.instagramui.data.LocalPostProvider.allStory
import com.shakiv.husain.instagramui.data.PostItem
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.ui.components.FeedListItem
import com.shakiv.husain.instagramui.ui.components.StoryListItem

@Composable
fun HomeFeed(
    postList: List<PostItem>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    postLazyListState: LazyListState,
    storyLazyListState: LazyListState,
    onItemClick: (PostItem) -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        PostList(postList = postList, postLazyListState = postLazyListState, storyLazyListState){
            onItemClick(it)
        }
    }
}

@Composable
fun PostList(
    postList: List<PostItem>,
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
            StoryList(allStory, storyLazyListState)
        }
        items(postList) { post ->
            FeedListItem(postItem = post) { postItem ->
                onItemClick(postItem)
            }
        }
    }

}

@Composable
fun StoryList(storyList: List<StoryItem>, storyLazyListState: LazyListState) {
    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = storyLazyListState
    ) {
        items(storyList) { story ->
            StoryListItem(storyItem = story)
        }
    }
}
