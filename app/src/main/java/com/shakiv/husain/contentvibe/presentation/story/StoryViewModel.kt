package com.shakiv.husain.contentvibe.presentation.story

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.StoryItem
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : ContentVibeViewModel() {


    private val _storyState = MutableStateFlow(ViewStoryUIState())

    val storyUIState = _storyState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _storyState.value
    )

    fun setStoryItem(storyItem: StoryItem) {
        _storyState.update {
            it.copy(
                storyItem = storyItem,
                isLoading = false
            )
        }
    }


    fun storyViewed(storyItem: StoryItem) {

        val storyViewedUsersList : MutableList<UserEntity> = _storyState.value.storyItem?.viewedUsers ?: mutableListOf()
        val currentUserId = accountService.currentUserId.orEmpty()
        val isStoryBelongsToCurrentUser = storyItem.user?.userId.orEmpty().contentEquals(currentUserId)

        val isViewed = storyViewedUsersList.filter { it.userId == currentUserId }

        viewModelScope.launch {

            val currentUser = accountService.getUserById(currentUserId)

            if (isViewed.isEmpty()) {
                storyViewedUsersList.add(currentUser ?: return@launch)
            }

            storageService.updateStory(
                storyItem.copy(
                    viewedUsers = storyViewedUsersList
                )
            )
        }
    }

}