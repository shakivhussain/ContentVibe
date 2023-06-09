package com.shakiv.husain.instagramui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.data.PostItem
import com.shakiv.husain.instagramui.ui.components.FeedListItem

@Composable
fun HomeFeed(postList: List<PostItem>) {
    PostList(postList)
}

@Composable
fun PostList(postList: List<PostItem>) {
    LazyColumn(
        modifier = Modifier, contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(postList) { post ->
            FeedListItem(postItem = post)
        }
    }

}