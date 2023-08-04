package com.shakiv.husain.instagramui.presentation.home

import com.shakiv.husain.instagramui.data.StoryItem
import com.shakiv.husain.instagramui.data.post.PostItem
import com.shakiv.husain.instagramui.utils.ErrorMessage

data class HomeViewModelState(
    val posts: List<PostItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: List<ErrorMessage> = emptyList(),
    val stories: List<StoryItem> = emptyList()
) {
//    fun toUiState(): HomeUiState = if (posts == null) {
//        HomeUiState.NoPosts(
//            isLoading = isLoading, errorMessage = errorMessage
//        )
//    } else {
//        HomeUiState.HasPosts(
//            postFeed = posts, isLoading = isLoading, errorMessage = errorMessage
//        )
//    }
}
