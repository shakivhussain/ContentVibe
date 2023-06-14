package com.shakiv.husain.instagramui.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.ui.IconsInstagram


@Composable
fun StoryListItem(storyItem: StoryItem, modifier: Modifier = Modifier) {

    Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(6.dp))
        ProfileImage(profilePath = storyItem.storyImage)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = storyItem.userName,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(2.dp)
                .width(90.dp),
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