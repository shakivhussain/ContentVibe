package com.shakiv.husain.instagramui.data.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.data.Resource
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.utils.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


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


private data class HomeViewModelState(
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


@HiltViewModel
class HomeViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {


    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true
        )
    )

    val uiState: StateFlow<HomeUiState> = viewModelState.map(HomeViewModelState::toUiState).stateIn(
            viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState()
        )

    init {
        refreshPost()
    }

    private fun refreshPost() {

        viewModelState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val result = postRepository.getPostFeed()
            viewModelState.update {
                when (result) {
                    is Resource.Success -> {
                        it.copy(
                            postFeed = result.data, isLoading = false
                        )

                    }

                    is Resource.Error -> {
                        val errorMessage = it.errorMessage + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(
                            errorMessage = errorMessage, isLoading = false
                        )
                    }
                }
            }
        }
    }

//    companion object {
//        fun provideFactory(postRepository: PostRepository): ViewModelProvider.Factory =
//            object : ViewModelProvider.Factory {
//
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return HomeViewModel(postRepository) as T
//                }
//
//            }
//    }

}