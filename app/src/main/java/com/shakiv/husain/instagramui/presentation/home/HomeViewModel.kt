package com.shakiv.husain.instagramui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.data.Resource
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.domain.service.PostRepository
import com.shakiv.husain.instagramui.domain.service.StorageService
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val accountService: AccountService,
    private val storageService: StorageService
) : ViewModel() {


    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true
        )
    )

    val uiState: StateFlow<HomeUiState> = viewModelState.map(HomeViewModelState::toUiState).stateIn(
            viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState()
        )

    init {
        refreshData()
    }


    private fun refreshData() {

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

}