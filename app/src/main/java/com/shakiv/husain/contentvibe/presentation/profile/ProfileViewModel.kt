package com.shakiv.husain.contentvibe.presentation.profile

import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.data.mapper.toUser
import com.shakiv.husain.contentvibe.domain.model.ProfileUIState
import com.shakiv.husain.contentvibe.domain.model.User
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import com.shakiv.husain.contentvibe.utils.extentions.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,

    ) : ContentVibeViewModel() {

    private val profileViewModeState = MutableStateFlow(ProfileUIState())

    val profileUIStateVM: StateFlow<ProfileUIState> = profileViewModeState.stateIn(
        viewModelScope, SharingStarted.Eagerly, profileViewModeState.value
    )

    init {
        fetchUserDetails()
    }


    fun fetchUserDetails(userId: String = "") {

        val id = userId.ifBlank { accountService.currentUserId }

        launchCatching(errorBlock = {
            logd("Profile VM Error : $it")
        }) {
            val user = accountService.getUserById(id)

            profileViewModeState.update {
                it.copy(
                    user = user?.toUser() ?: User()
                )
            }

        }


        viewModelScope.launch {
            delay(2000)
            logd("After 2 Sec Changed $userId")
            profileViewModeState.update {
                it.copy(
                    userName = "Shakib Mansoori"
                )
            }

        }


    }
}

