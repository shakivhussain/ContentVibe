package com.shakiv.husain.contentvibe.presentation.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.contentvibe.domain.model.NavigationArgsState
import com.shakiv.husain.contentvibe.utils.ImageUtils


@Composable
fun StoryViewScreen(
    storyViewModel: StoryViewModel = hiltViewModel(),
    navigationArgsState: NavigationArgsState
) {

    val storyUIState by storyViewModel.storyUIState.collectAsStateWithLifecycle(
        initialValue = ViewStoryUIState()
    )

    val storyItem by remember() { mutableStateOf(navigationArgsState.storyItem) }


    LaunchedEffect(key1 = storyItem){
        storyItem?.let {
            storyViewModel.setStoryItem(storyItem = it)
        }
    }

    StoryViewContent(storyUIState )


}

@Preview
@Composable
fun PreviewStoryViewContent() {

    StoryViewContent(ViewStoryUIState())
}

@Composable
fun StoryViewContent(
    viewStoryUIState: ViewStoryUIState,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {},
        bottomBar = {},
        floatingActionButton = {

        }

    ) {

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ImageUtils.SetImage(
                modifier = Modifier.fillMaxSize(),
                imagePath = viewStoryUIState.storyItem?.storyImage.orEmpty(),
                showLoading = viewStoryUIState.isLoading
            )
        }
    }
}
