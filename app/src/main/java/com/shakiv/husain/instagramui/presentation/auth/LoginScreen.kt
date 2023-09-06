package com.shakiv.husain.instagramui.presentation.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shakiv.husain.instagramui.domain.model.Resource
import com.shakiv.husain.instagramui.presentation.app.HomeDestination
import com.shakiv.husain.instagramui.presentation.common.composable.BasicButton
import com.shakiv.husain.instagramui.presentation.common.composable.EmailField
import com.shakiv.husain.instagramui.presentation.common.composable.PasswordField
import com.shakiv.husain.instagramui.presentation.common.composable.ProgressBar
import com.shakiv.husain.instagramui.utils.extentions.fieldModifier
import com.shakiv.husain.instagramui.utils.snackbar.SnackBarManager
import com.shakiv.husain.instagramui.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {

    val uiState = authViewModel.loginUiState


    Scaffold(
        topBar = {

        },
        bottomBar = {

        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val fieldModifier = Modifier.fieldModifier()

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge
            )

            EmailField(
                value = uiState.email,
                onNewValue = authViewModel::onEmailChange,
                modifier = fieldModifier
            )
            PasswordField(
                value = uiState.password,
                onNewValue = authViewModel::onPasswordChange,
                modifier = fieldModifier
            )

            BasicButton(text = AppText.login) {
                authViewModel.onLoginClick()
            }

            BasicButton(text = AppText.forgot_password) {
                authViewModel.sendResetPasswordLink()
            }

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
