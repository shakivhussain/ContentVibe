package com.shakiv.husain.contentvibe.presentation.profile

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.data.LocalPostProvider.allUserPost
import com.shakiv.husain.contentvibe.data.mapper.toPost
import com.shakiv.husain.contentvibe.domain.model.NavigationArgsState
import com.shakiv.husain.contentvibe.domain.model.ProfileUIState
import com.shakiv.husain.contentvibe.presentation.common.composable.EmptyComingSoon
import com.shakiv.husain.contentvibe.presentation.common.composable.ProgressBar
import com.shakiv.husain.contentvibe.presentation.home.FeedListItem
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.ImageUtils.SetProfileImage
import com.shakiv.husain.contentvibe.utils.extentions.logd
import com.shakiv.husain.contentvibe.R.string as AppText


@Preview()
@Composable
fun PreviewProfile() {
    ProfileScreen(onBackPressed = {})
}

@Composable
fun ProfileScreen(
    navigationArgsState: NavigationArgsState? = null,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {

    val profileUIState by profileViewModel
        .profileViewModeState
        .collectAsStateWithLifecycle()


    logd("UserPosts : ${profileUIState.posts.size}")


    LaunchedEffect(navigationArgsState) {

        val userId = navigationArgsState?.userId.orEmpty()
        if (userId.isNotBlank()) {
            profileViewModel.fetchUserDetails(userId)
        }
    }


    val user = profileUIState.user

    logd("FetchUser : $user")


    ProfileScreen(
        profileUIState = profileUIState,
        onNotificationClick = {},
        onMoreOptionClick = {},
        onBackPressed = onBackPressed
    )

    BackHandler(true) {
        onBackPressed()

    }
}


@Composable
fun ProfileScreen(
    profileUIState: ProfileUIState,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
    onBackPressed : () -> Unit
) {
    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                modifier = Modifier,
                title = profileUIState.userName,
                onNotificationClick = onNotificationClick,
                onMoreOptionClick = onMoreOptionClick,
                onBackPressed = onBackPressed
            )
            ProfilePager(profileUIState)
        }
    }
}

@Composable
fun ProfileHeader(
    profileUIState: ProfileUIState,
    onNotificationClick: () -> Unit,
    onMoreOptionClick: () -> Unit,
) {
    val user = profileUIState.user

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        SetProfileImage(
            modifier = Modifier.size(70.dp),
            imagePath = profileUIState.user.profileUrl.orEmpty(),
            onProfileClick = {}
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TitleSubtitle(title = "1.1K", subtitle = stringResource(id = AppText.posts))
            TitleSubtitle(title = "131.K", subtitle = stringResource(id = AppText.follower))
            TitleSubtitle(title = "1.2K", subtitle = stringResource(id = AppText.following))
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
        text = user.userName,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

    if (user.userDescription.isNotBlank()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = user.userDescription.orEmpty(),
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )


        Spacer(modifier = Modifier.height(16.dp))

    }


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
fun ProfilePager(
    profileUIState: ProfileUIState
) {

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
            ProfileHeader(
                profileUIState,
                {},
                {},
            )
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
                profileUIState.posts
            }

            ProfileTabs.REELS.ordinal -> {
//                emptyList()
                profileUIState.posts
            }

            ProfileTabs.PROFILE.ordinal -> {
                profileUIState.posts
            }

            else -> {
                profileUIState.posts
//                allUserPost()
            }
        }


        if (profileUIState.isPostsLoading) {
            stickyHeader {
                Spacer(Modifier.height(50.dp))
                ProgressBar()
            }
        } else {
            items(userPosts) { post ->
                FeedListItem(post = post,
                    onItemClick = {}, onLikeClick = {}, onMoreOptionClick = {},
                    onCommentClicked = {},
                    onProfileClick = {},
                    onShareClicked = {})
            }
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

            FeedListItem(post = it.toPost(),
                onItemClick = {},
                onLikeClick = {},
                onMoreOptionClick = {},
                onCommentClicked = {},
                onProfileClick = {},
                onShareClicked = {}
            )

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
    onBackPressed : () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {

            IconButton(onClick = { onBackPressed() }) {
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


