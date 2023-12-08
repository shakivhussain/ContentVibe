package com.shakiv.husain.contentvibe.presentation.profile

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.mapper.toPost
import com.shakiv.husain.contentvibe.data.mapper.toUser
import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem
import com.shakiv.husain.contentvibe.domain.model.ProfileUIState
import com.shakiv.husain.contentvibe.domain.model.User
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.domain.service.StorageService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import com.shakiv.husain.contentvibe.utils.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : ContentVibeViewModel() {

    private val _profileViewModeState = MutableStateFlow(ProfileUIState())

    val profileViewModeState: StateFlow<ProfileUIState> = _profileViewModeState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _profileViewModeState.value
    )

    init {
        fetchUserDetails()
    }


    fun fetchUserDetails(userId: String = "") {

        val id = userId.ifBlank { accountService.currentUserId }

        launchCatching(errorBlock = {

            _profileViewModeState.update { profileUiState ->
                profileUiState.copy(
                    errorInProfileDetails = it
                )
            }


        }) {
            val user = accountService.getUserById(id)

            _profileViewModeState.update {
                it.copy(
                    user = user?.toUser() ?: User(),
                    isProfileLoading = false
                )
            }

            fetchPostsByUserId()

        }
    }


    fun getBottomSheetItems(): List<BottomSheetItem> {
        return listOf(
            BottomSheetItem.LOGOUT,
            BottomSheetItem.DISMISS,
        )

    }


    fun onItemClickListener(bottomSheetItem: BottomSheetItem, onUserLogout : () -> Unit) {

        when (bottomSheetItem) {
            BottomSheetItem.LOGOUT -> {
                logoutUser()
                onUserLogout()
            }

            BottomSheetItem.DISMISS -> {
                SnackBarManager.showMessage("Dismissed Bottom Sheet")
            }

            else -> {

            }
        }
    }

    private fun logoutUser() {

        launchCatching(errorBlock = {}) {
            accountService.signOut()
            _profileViewModeState.update { profileUIState: ProfileUIState ->
                profileUIState.copy(
                    isUserLogout = true
                )
            }
        }

    }


    fun fetchPostsByUserId() {

        val userId = _profileViewModeState.value.user.userId
        val currentUserId = accountService.currentUserId.orEmpty()


        launchCatching(
            errorBlock = {

                _profileViewModeState.update { profileUiState ->
                    profileUiState.copy(
                        errorInPosts = it
                    )
                }
            }
        ) {
            storageService.getPostsBy(userId).collectLatest { postEntities ->

                if (!postEntities.isNullOrEmpty()) {
                    _profileViewModeState.update { profileUiState ->
                        profileUiState.copy(
                            posts = postEntities.map { postEntity ->
                                postEntity.isLiked =
                                    postEntity.currentUserLike.contains(currentUserId)
                                postEntity.toPost()
                            },

                            )
                    }
                }

                _profileViewModeState.update { profileUiState ->
                    profileUiState.copy(
                        isPostsLoading = false,
                        isPostsEmpty = postEntities.isNullOrEmpty()
                    )
                }

            }
        }
    }
}

