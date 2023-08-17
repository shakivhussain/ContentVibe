package com.shakiv.husain.instagramui.presentation.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.data.LocalPostProvider.allUserPost
import com.shakiv.husain.instagramui.data.mapper.toPost
import com.shakiv.husain.instagramui.presentation.common.composable.EmptyComingSoon
import com.shakiv.husain.instagramui.presentation.common.composable.ProfileImage
import com.shakiv.husain.instagramui.presentation.home.FeedListItem
import com.shakiv.husain.instagramui.utils.ImageUtils


@Preview()
@Composable
fun PreviewProfile() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    ProfileScreen(
        title = "Shakiv Husain",
        onNotificationClick = {},
        onMoreOptionClick = {},
    )
}


@Composable
fun ProfileScreen(
    title: String,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
) {
    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                modifier = Modifier,
                title = title,
                onNotificationClick = onNotificationClick,
                onMoreOptionClick = onMoreOptionClick
            )

            ProfilePager()
        }
    }
}

@Composable
fun ProfileHeader(
    onNotificationClick: () -> Unit, onMoreOptionClick: () -> Unit, title: String
) {


    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            profilePath = R.drawable.profile_pic,
            modifier = Modifier.size(70.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TitleSubtitle(title = "13.K", subtitle = "Posts")
            TitleSubtitle(title = "13.K", subtitle = "Posts")
            TitleSubtitle(title = "13.K", subtitle = "Posts")

        }

    }

    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = "Shakiv Husain",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = stringResource(id = R.string.placeholder_desc),
        maxLines = 6,
        overflow = TextOverflow.Ellipsis
    )


    Spacer(modifier = Modifier.height(16.dp))


    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.outline
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
            Text(text = "Follow")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp), onClick = { /*TODO*/ },
            colors = buttonColor
        ) {
            Text(text = "Message")
        }

        Button(
            modifier = Modifier
                .weight(.5f)
                .padding(start = 8.dp),
            colors = buttonColor,
            onClick = { /*TODO*/ }) {
            ImageUtils.setImage(imageId = R.drawable.ic_add_person)
        }

    }

    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfilePager() {

    var selectedIndex by remember {
        mutableStateOf(ProfileTabs.POSTS.ordinal)
    }

    val tabList = remember {
        ProfileTabs.values().map { it.tabName }
    }


    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        item {
            ProfileHeader({}, {}, "Shakiv")
        }


        stickyHeader {
            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                tabList.forEachIndexed { index, title ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = {
                            when (title) {
                                ProfileTabs.POSTS.tabName -> {
                                    selectedIndex = ProfileTabs.POSTS.ordinal
                                }

                                ProfileTabs.REELS.tabName -> {
                                    selectedIndex = ProfileTabs.REELS.ordinal
                                }

                                ProfileTabs.PROFILE.tabName -> {
                                    selectedIndex = ProfileTabs.PROFILE.ordinal
                                }
                            }
                        },
                        text = {
                            Text(text = title)
                        }
                    )
                }
            }
        }

        val userPosts = when (selectedIndex) {
            ProfileTabs.POSTS.ordinal -> {
                allUserPost()
            }

            ProfileTabs.REELS.ordinal -> {
                emptyList()
            }

            ProfileTabs.PROFILE.ordinal -> {
                allUserPost()
            }

            else -> {
                allUserPost()
            }
        }

        items(userPosts) {
            FeedListItem(post = it.toPost(), onItemClick = {}, onLikeClick = {})
        }


    }


}

@Composable fun TabProfileScreen() {
    EmptyComingSoon(subTitle = "")
}

@Composable fun UserReelsScreen() {
    EmptyComingSoon(subTitle = "")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPostScreen() {


    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
//        stickyHeader {
//            Text(text = "Shakiv", modifier = Modifier.fillMaxWidth(1F).height(100.dp))
//        }
        items(allUserPost()) {

            FeedListItem(post = it.toPost(), onItemClick = {}, onLikeClick = {})

        }
    }

}


private enum class ProfileTabs(val tabName: String) {
    POSTS("Posts"),
    REELS("Reels"),
    PROFILE("Profile"),

}


@Composable
fun TitleSubtitle(title: String, subtitle: String) {


    Column(
        modifier = Modifier.padding(0.dp),
    ) {
        Text(text = title)
        Text(text = subtitle)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier,
    title: String,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {

            IconButton(onClick = { /*TODO*/ }) {
                ImageUtils.setImage(
                    imageId = R.drawable.ic_arrow_back, modifier = Modifier.padding(start = 0.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                )
            }

        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.inverseSurface,
            actionIconContentColor = MaterialTheme.colorScheme.inverseSurface,
            titleContentColor = MaterialTheme.colorScheme.inverseSurface
        ),
        actions = {
            IconButton(onClick = onNotificationClick) {
                ImageUtils.setImage(
                    imageId = R.drawable.ic_notifications,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                )
            }

            IconButton(onClick = onMoreOptionClick) {
                ImageUtils.setImage(
                    imageId = R.drawable.ic_more,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)

                )
            }
        }
    )

}


