package com.shakiv.husain.contentvibe.presentation.home

import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.domain.model.Post
import com.shakiv.husain.contentvibe.utils.ErrorMessage

data class HomeViewModelState(
    val posts: List<Post> = emptyList(),
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
