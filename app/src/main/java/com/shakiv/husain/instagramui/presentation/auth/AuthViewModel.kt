package com.shakiv.husain.instagramui.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.presentation.app.ContentVibeViewModel
import com.shakiv.husain.instagramui.presentation.app.HomeDestination
import com.shakiv.husain.instagramui.utils.extentions.isValidEmail
import com.shakiv.husain.instagramui.utils.extentions.isValidPassword
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shakiv.husain.instagramui.R.string as AppText

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountService: AccountService
) : ContentVibeViewModel() {

    var loginUiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = loginUiState.value.email


    private val password
        get() = loginUiState.value.password


    fun signInWithCredential(authCredential: AuthCredential) {
        viewModelScope.launch {
            accountService.signInWithCredential(authCredential)
        }
    }

    fun signInWithEmail(openAndPopUp: (String) -> Unit) {

        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
        }

        if (!password.isValidPassword()) {
            SnackBarManager.showMessage(AppText.empty_password_error)
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(HomeDestination.route)
        }
    }

}