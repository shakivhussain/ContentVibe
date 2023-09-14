package com.shakiv.husain.instagramui.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.AuthCredential
import com.shakiv.husain.instagramui.domain.model.Resource
import com.shakiv.husain.instagramui.domain.service.AccountService
import com.shakiv.husain.instagramui.presentation.app.ContentVibeViewModel
import com.shakiv.husain.instagramui.presentation.app.HomeDestination
import com.shakiv.husain.instagramui.utils.extentions.isValidEmail
import com.shakiv.husain.instagramui.utils.extentions.isValidPassword
import com.shakiv.husain.instagramui.utils.extentions.passwordMatches
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.shakiv.husain.instagramui.R.string as AppText

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val accountService: AccountService
) : ContentVibeViewModel() {


    var signUpState by mutableStateOf<Resource<Boolean>>(Resource.Success(null))
        private set

    var loginState by mutableStateOf<Resource<Boolean>>(Resource.Success(null))
        private set

    var sendVerificationState by mutableStateOf<Resource<Boolean>>(Resource.Success(null))
        private set

    var sendResetPasswordState by mutableStateOf<Resource<Boolean>>(Resource.Success(false))
        private set

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    var signInWithGoogle by mutableStateOf<Resource<Boolean>>((Resource.Success(null)))
        private set

    val hasUser = accountService.hasUser

    private val email
        get() = loginUiState.email

    private val password
        get() = loginUiState.password

    fun signInWithCredential(authCredential: AuthCredential) {

        launchCatching(errorBlock = {
            signInWithGoogle = Resource.Error(message = it)
        }) {
            signInWithGoogle = Resource.Loading()
            accountService.signInWithCredential(authCredential)
            signInWithGoogle = Resource.Success(true)
        }

    }

    fun onEmailChange(newValue: String) {
        loginUiState = loginUiState.copy(
            email = newValue
        )
    }

    fun onPasswordChange(newValue: String) {
        loginUiState = loginUiState.copy(
            password = newValue
        )
    }

    fun onConfirmPassword(newValue: String) {
        loginUiState = loginUiState.copy(
            confirmPassword = newValue
        )
    }

    fun onSignUpClick(openAndPopUp: (String) -> Unit) {

        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackBarManager.showMessage(AppText.empty_password_error)
            return
        }

        if (!password.passwordMatches(loginUiState.confirmPassword)) {
            SnackBarManager.showMessage(AppText.confirm_password)
            return
        }

        launchCatching(
            errorBlock = {
                signUpState = Resource.Error(message = it)
            }
        ) {
            signUpState = Resource.Loading()
            accountService.signUpWithEmail(email, password)
            signUpState = Resource.Success(true)
            openAndPopUp(HomeDestination.route)
        }

    }


    fun onLoginClick() {

        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackBarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching(errorBlock = {
            loginState = Resource.Error(message = it)
        }) {
            loginState = Resource.Loading()
            accountService.authenticate(email, password)
            loginState = Resource.Success(true)
        }
    }

    fun sendEmailVerification() {
        launchCatching(errorBlock = {
            sendVerificationState = Resource.Error(message = it)
        }) {
            sendVerificationState = Resource.Loading()
            accountService.sendEmailVerification()
            sendVerificationState = Resource.Success(true)
        }
    }

    fun sendResetPasswordLink() {
        launchCatching(errorBlock = {

            sendResetPasswordState = Resource.Error(message = it)
        }) {
            sendResetPasswordState = Resource.Loading()
            accountService.sendResetPasswordLink(email)
            sendResetPasswordState = Resource.Success(true)
        }
    }

}