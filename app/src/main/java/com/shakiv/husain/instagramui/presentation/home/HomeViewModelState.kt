package com.shakiv.husain.instagramui.presentation.home

import com.shakiv.husain.instagramui.data.post.PostFeed
import com.shakiv.husain.instagramui.utils.ErrorMessage

data class HomeViewModelState(
    val postFeed: PostFeed? = null,
    val isLoading: Boolean = false,
    val errorMessage: List<ErrorMessage> = emptyList(),
) {
    fun toUiState(): HomeUiState = if (postFeed == null) {
        HomeUiState.NoPosts(
            isLoading = isLoading, errorMessage = errorMessage
        )
    } else {
        HomeUiState.HasPosts(
            postFeed = postFeed, isLoading = isLoading, errorMessage = errorMessage
        )
    }
}
