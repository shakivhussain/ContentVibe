package com.shakiv.husain.contentvibe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.shakiv.husain.contentvibe.domain.model.MainUiState
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.presentation.app.ContentVibeViewModel
import com.shakiv.husain.contentvibe.presentation.auth.AuthViewModel
import com.shakiv.husain.contentvibe.presentation.auth.LoginUiState
import com.shakiv.husain.contentvibe.utils.extentions.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accountService : AccountService
) : ContentVibeViewModel() {

    var mainActivityUiState : StateFlow<MainUiState> = accountService.currentUser.map {
        logd("mainActivityUiState : VM : $it")
        MainUiState(
            isUserLogIn = it.userId.isNullOrEmpty().not(),
            userName = it.userName
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainUiState(),
        started = SharingStarted.WhileSubscribed(5_000)
    )
        private set

    val currentUser = accountService.currentUser


}