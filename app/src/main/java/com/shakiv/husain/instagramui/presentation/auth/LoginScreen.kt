package com.shakiv.husain.instagramui.presentation.auth

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.shakiv.husain.instagramui.domain.model.Resource
import com.shakiv.husain.instagramui.presentation.app.HomeDestination
import com.shakiv.husain.instagramui.presentation.common.composable.BasicButton
import com.shakiv.husain.instagramui.presentation.common.composable.EmailField
import com.shakiv.husain.instagramui.presentation.common.composable.PasswordField
import com.shakiv.husain.instagramui.presentation.common.composable.ProgressBar
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarManager
import com.shakiv.husain.instagramui.R.string as AppText

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {
    val uiState = authViewModel.loginUiState

    Column {

        EmailField(value = uiState.email, onNewValue = authViewModel::onEmailChange)
        PasswordField(value = uiState.password, onNewValue = authViewModel::onPasswordChange)

        BasicButton(text = AppText.login) {
            authViewModel.onLoginClick()
        }

        BasicButton(text = AppText.forgot_password) {
            authViewModel.sendResetPasswordLink()
        }

    }

    Login(
        navigateToNextScreen = navigateToNextScreen
    )

    ForgotPassword(
        showResetPasswordMessage = {
            SnackBarManager.showMessage(AppText.forgot_password_message)
        }
    )

}

@Composable
fun ForgotPassword(
    authViewModel: AuthViewModel = hiltViewModel(),
    showResetPasswordMessage: () -> Unit
) {
    when (val sendResetPasswordState = authViewModel.sendResetPasswordState) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isPasswordResetEmailSend = sendResetPasswordState.data ?: false
            LaunchedEffect(key1 = isPasswordResetEmailSend) {
                if (isPasswordResetEmailSend) {
                    showResetPasswordMessage()
                }
            }
        }

        is Resource.Error -> {}
    }


}

@Composable
fun Login(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {

    when (val loginState = authViewModel.loginState) {
        is Resource.Loading -> {
            Log.d("TAGContent", "Login: Loading")
            ProgressBar()
        }

        is Resource.Success -> {
            val isUserSignedIn = loginState.data ?: false
            LaunchedEffect(key1 = isUserSignedIn) {
                if (isUserSignedIn) {
                    navigateToNextScreen(HomeDestination.route)
                }
            }
        }

        is Resource.Error -> {
            val errorMessage = loginState.message.orEmpty()
            Log.e("TAGContent", "Login: Error $errorMessage")
        }
    }
}
