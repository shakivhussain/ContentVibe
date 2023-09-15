package com.shakiv.husain.contentvibe.presentation.home

import com.shakiv.husain.contentvibe.data.model.PostEntity
import com.shakiv.husain.contentvibe.utils.ErrorMessage

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessage: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean, override val errorMessage: List<ErrorMessage>
    ) : HomeUiState

    data class HasPosts(
        val postFeed: List<PostEntity>,
        override val isLoading: Boolean,
        override val errorMessage: List<ErrorMessage>
    ) : HomeUiState



}