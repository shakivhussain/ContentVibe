package com.shakiv.husain.contentvibe.presentation.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.model.PostActions
import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem
import com.shakiv.husain.contentvibe.domain.model.Post
import com.shakiv.husain.contentvibe.presentation.common.composable.CommentBottomSheet
import com.shakiv.husain.contentvibe.presentation.common.composable.MoreOptionBottomSheet
import com.shakiv.husain.contentvibe.presentation.common.composable.ProfileImage
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.extentions.logd


@Preview
@Composable
fun PreviewStoryListItem() {
    val user = UserEntity(
        "$1 Shakiv Husain", isAnonymous = true, "Professional",
        userProfile = "contentvibe.ProfilePic"
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

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeFeed(
    onItemClick: (Post) -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val moreOptionBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val commentBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isMoreOptionBottomSheetVisible by remember { mutableStateOf(false) }
    var isCommentBottomSheetVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    HomeFeed(
        uiState = uiState,
        onItemClick = {
            return@HomeFeed
            onItemClick(it)
        },
        onLiked = {


            mainViewModel.onPostLiked(it)
        },
        onMoreOptionIconClick = {
            mainViewModel.onMoreOptionIconClick(it)
            isMoreOptionBottomSheetVisible = !isMoreOptionBottomSheetVisible
        },
        onCommentClicked = {
            logd(" 1 Comment Clicked")

            isCommentBottomSheetVisible = !isCommentBottomSheetVisible
        },
        onShareClicked = {
            logd(" 2 Comment Clicked")
            isCommentBottomSheetVisible = !isCommentBottomSheetVisible
        }
    )

    ShowMoreOptionBottomSheet(
        moreOptionBottomSheetState,
        isMoreOptionBottomSheetVisible,
        onDismiss = {
            isMoreOptionBottomSheetVisible = false
        },
        itemsLists = mainViewModel.getBottomSheetItems(),
        onItemClick = {
            mainViewModel.onItemClickMoreOption(it)
            isMoreOptionBottomSheetVisible = false
        }
    )

    ShowCommentBottomSheet(
        bottomSheetState = commentBottomSheetState,
        onDismiss = {
            isCommentBottomSheetVisible = false
        },
        isBottomSheetVisible = isCommentBottomSheetVisible,
    )

}

@Composable
fun HomeFeed(
    uiState: HomeViewModelState,
    onItemClick: (Post) -> Unit,
    onLiked: (Post) -> Unit,
    onMoreOptionIconClick: (Post) -> Unit,
    onCommentClicked: (Post) -> Unit,
    onShareClicked: (Post) -> Unit,
) {

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
            onMoreOptionClick = onMoreOptionIconClick,
            onLiked = { onLiked(it) },
            onCommentClicked = onCommentClicked,
            onShareClicked = onShareClicked
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
    onMoreOptionClick: (Post) -> Unit,
    onLiked: (Post) -> Unit,
    onCommentClicked: (Post) -> Unit,
    onShareClicked: (Post) -> Unit,
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
                Divider(
                    Modifier.fillMaxWidth(), thickness = .2.dp,
                )
            }
            items(postList) { post ->

                FeedListItem(
                    onLikeClick = { onLiked(post) }, post = post,
                    onMoreOptionClick = onMoreOptionClick,
                    onItemClick = {
                        onItemClick(it)
                    },
                    onCommentClicked = {
                        onCommentClicked(post)
                    },
                    onShareClicked = {
                        onShareClicked(post)
                    }
                )
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
            text = "ContentVibe", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = IconsContentVibe.LIKE),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
            )
        }


        IconButton(onClick = { /*TODO*/ }) {
            Image(

                painter = painterResource(id = IconsContentVibe.CHAT),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCommentBottomSheet(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    isBottomSheetVisible: Boolean = false,
) {

    if (isBottomSheetVisible) {
        CommentBottomSheet(
            bottomSheetState,
            onDismiss
        )

    }


    LaunchedEffect(key1 = isBottomSheetVisible) {
        if (isBottomSheetVisible)
            bottomSheetState.expand()
    }

    LaunchedEffect(key1 = isBottomSheetVisible) {
        if (isBottomSheetVisible) bottomSheetState.show() else bottomSheetState.hide()
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMoreOptionBottomSheet(
    bottomSheetState: SheetState,
    isBottomSheetVisible: Boolean = false,
    itemsLists: List<BottomSheetItem> = emptyList(),
    onDismiss: () -> Unit,
    onItemClick: (BottomSheetItem) -> Unit,
) {
    if (isBottomSheetVisible) {
        MoreOptionBottomSheet(
            onItemClick = onItemClick,
            sheetState = bottomSheetState,
            onDismissListener = {
                onDismiss()
            },
            itemsLists = itemsLists
        )
    }

    LaunchedEffect(key1 = isBottomSheetVisible) {
        if (isBottomSheetVisible) bottomSheetState.show() else bottomSheetState.hide()
    }

}



