package com.shakiv.husain.contentvibe.presentation.story

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val accountService: AccountService
) : ContentVibeViewModel() {


    private val _storyState = MutableStateFlow(ViewStoryUIState())

    val storyUIState = _storyState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _storyState.value
    )

    init {

        _storyState.update {
            it.copy(
                isLoading = false
            )
        }


    }


    fun setStoryItem(storyItem: StoryItem) {
        _storyState.update {
            it.copy(
                storyItem = storyItem
            )
        }
    }


}