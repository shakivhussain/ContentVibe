package com.shakiv.husain.instagramui.presentation.home

import com.shakiv.husain.instagramui.data.post.PostFeed
import com.shakiv.husain.instagramui.utils.ErrorMessage

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessage: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean, override val errorMessage: List<ErrorMessage>
    ) : HomeUiState

    data class HasPosts(
        val postFeed: PostFeed, override val isLoading: Boolean,
        override val errorMessage: List<ErrorMessage>
    ) : HomeUiState
}