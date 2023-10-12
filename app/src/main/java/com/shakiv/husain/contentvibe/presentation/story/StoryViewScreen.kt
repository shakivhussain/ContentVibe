package com.shakiv.husain.contentvibe.presentation.story

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shakiv.husain.contentvibe.data.MockService.getMockStoryItem
import com.shakiv.husain.contentvibe.domain.model.NavigationArgsState
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.extentions.logd


@Composable fun StoryViewScreen(
    storyViewModel: StoryViewModel = hiltViewModel(), navigationArgsState: NavigationArgsState
) {

    val storyUIState by storyViewModel.storyUIState.collectAsStateWithLifecycle(
        initialValue = ViewStoryUIState()
    )

    val storyItem by remember() { mutableStateOf(navigationArgsState.storyItem) }


    LaunchedEffect(key1 = storyItem) {
        storyItem?.let {
            storyViewModel.setStoryItem(storyItem = it)

        }
    }

    StoryViewContent(storyUIState, onSuccess = {
        logd("OnImageSuccess : Triggered")
        storyViewModel.storyViewed(storyUIState.storyItem ?: return@StoryViewContent)
    })


}

@Preview @Composable fun PreviewStoryViewContent() {
    StoryViewContent(
        ViewStoryUIState(storyItem = getMockStoryItem())
    )
}

@Composable fun StoryViewContent(
    viewStoryUIState: ViewStoryUIState,
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit = {},
) {

    val storyImage = viewStoryUIState.storyItem?.storyImage.orEmpty()

    Scaffold(
        topBar = {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {


                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier

                ) {
                    ImageUtils.SetProfileImage(
                        viewStoryUIState.storyItem?.user?.profileUrl.orEmpty(),
                        modifier = Modifier
                            .size(40.dp),
                        onProfileClick = { })
                }


                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    modifier = Modifier.weight(1F),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = viewStoryUIState.storyItem?.user?.userName.orEmpty(),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = viewStoryUIState.storyItem?.user?.description.orEmpty(),
                            style = MaterialTheme.typography.bodySmall, maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }


                }

                IconButton(
                    onClick = { },
                ) {

                    ImageUtils.setImage(
                        imageId = IconsContentVibe.IC_MoreOption, contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )

                }
            }
        },
        bottomBar = {},
        floatingActionButton = {

        }

    ) {

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Spacer(modifier = Modifier
                .height(16.dp)
                .fillMaxWidth())

            ImageUtils.SetImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
                imagePath = storyImage,
                showLoading = viewStoryUIState.isLoading,
                onSuccess = onSuccess,
                contentScale = ContentScale.Fit
            )


        }
    }
}
