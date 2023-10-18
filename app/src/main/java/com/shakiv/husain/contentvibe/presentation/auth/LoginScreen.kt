package com.shakiv.husain.contentvibe.presentation.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.GoogleAuthProvider
import com.shakiv.husain.contentvibe.domain.model.Resource
import com.shakiv.husain.contentvibe.presentation.app.DataStoreViewModel
import com.shakiv.husain.contentvibe.presentation.app.HomeDestination
import com.shakiv.husain.contentvibe.presentation.common.composable.EmailField
import com.shakiv.husain.contentvibe.presentation.common.composable.PasswordField
import com.shakiv.husain.contentvibe.presentation.common.composable.ProgressBar
import com.shakiv.husain.contentvibe.presentation.common.composable.RegularButton
import com.shakiv.husain.contentvibe.presentation.common.composable.RegularSmallButton
import com.shakiv.husain.contentvibe.utils.AppRoutes
import com.shakiv.husain.contentvibe.utils.DataStoreConstant.KEY_NEED_TO_SHOW_ONE_TAB_SIGN_IN
import com.shakiv.husain.contentvibe.utils.GoogleSignInUtils.handleGoogleSignInResult
import com.shakiv.husain.contentvibe.utils.GoogleSignInUtils.startGoogleSignIn
import com.shakiv.husain.contentvibe.utils.IconsContentVibe
import com.shakiv.husain.contentvibe.utils.ImageUtils
import com.shakiv.husain.contentvibe.utils.TimerUtils.Timer
import com.shakiv.husain.contentvibe.utils.extentions.fieldModifier
import com.shakiv.husain.contentvibe.utils.extentions.getContext
import com.shakiv.husain.contentvibe.utils.extentions.logd
import com.shakiv.husain.contentvibe.utils.snackbar.SnackBarManager
import com.shakiv.husain.contentvibe.R.string as AppText


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {


    val loginUiState = LoginUiState(
        email = "shakib@gmail.com",
        password = "shakib@gmail.com"
    )


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
    }
    LoginScreenContent(
        loginUiState = loginUiState,
        onEmailNewValue = {},
        onPasswordNewValue = {},
        onLoginClick = { /*TODO*/ },
        redirectToSignupScreen = {},
        sendResetPasswordLink = {},
        launcher = launcher

    )
}

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    navigateToHomeScreen: (String) -> Unit,
    redirectToSignupScreen: (String) -> Unit,

    ) {

    val uiState = authViewModel.loginUiState
    val userPreferences by dataStoreViewModel.userPreferencesFlow.collectAsStateWithLifecycle(
        initialValue = null
    )

    if (uiState.isUserLogIn)
        navigateToHomeScreen(HomeDestination.route)



    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        handleGoogleSignInResult(it.data) { credential ->
            authViewModel.signInWithCredential(credential)
        }
    }

    val oneTabLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credential =
                    authViewModel.oneTabClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credential.googleIdToken

                val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                authViewModel.signInWithCredential(googleCredential)
            } catch (it: Exception) {
                // TODO : Log Error
            }
        }
    }


    LoginScreenContent(
        uiState,
        onEmailNewValue = authViewModel::onEmailChange,
        onPasswordNewValue = authViewModel::onPasswordChange,
        onLoginClick = { authViewModel.onLoginClick() },
        sendResetPasswordLink = {
            authViewModel.sendResetPasswordLink()

        },
        redirectToSignupScreen = redirectToSignupScreen,
        launcher
    )


    LoginUser(navigateToNextScreen = navigateToHomeScreen)

    ForgotPassword(
        showResetPasswordMessage = {
            SnackBarManager.showMessage(AppText.forgot_password_message)
        }
    )


    userPreferences?.needToShowOneTabSignIn?.let { showOneTab ->
        Timer { count ->
            if (count == 1 && showOneTab) {
                authViewModel.oneTabSignInWithGoogle()
                dataStoreViewModel.updatePreferences(
                    KEY_NEED_TO_SHOW_ONE_TAB_SIGN_IN, false
                )

            }
        }
    }


    GoogleSignIn(
        authViewModel, isGoogleSignInSuccessfully = { navigateToHomeScreen(HomeDestination.route) })

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest
            .Builder(signInResult.pendingIntent.intentSender)
            .build()
        oneTabLauncher.launch(intent)
    }

    OneTabSignInWithGoogle(
        authViewModel = authViewModel,
        launch = {
            launch(it)
        }
    )


}

@Composable
fun GoogleSignIn(
    authViewModel: AuthViewModel,
    isGoogleSignInSuccessfully: () -> Unit
) {
    when (val googleSignIn = authViewModel.signInWithGoogle) {

        is Resource.Loading -> {
            ProgressBar()
        }

        is Resource.Success -> {

            val isGoogleSignInSuccess = googleSignIn.data ?: false
            LaunchedEffect(key1 = isGoogleSignInSuccessfully) {
                if (isGoogleSignInSuccess) {
                    isGoogleSignInSuccessfully()
                }
            }
        }

        is Resource.Error -> {}
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    loginUiState: LoginUiState,
    onEmailNewValue: (String) -> Unit,
    onPasswordNewValue: (String) -> Unit,
    onLoginClick: () -> Unit,
    sendResetPasswordLink: () -> Unit,
    redirectToSignupScreen: (String) -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {

    val context = getContext()


    Scaffold() {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val fieldModifier = Modifier.fieldModifier()

            ImageUtils.setImage(
                modifier = Modifier.fillMaxWidth(.5F),
                imageId = IconsContentVibe.IC_LOGIN
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(45.dp))

            EmailField(
                value = loginUiState.email,
                onNewValue = onEmailNewValue,
                modifier = fieldModifier
            )
            PasswordField(
                value = loginUiState.password,
                onNewValue = onPasswordNewValue,
                modifier = fieldModifier
            )


            Spacer(modifier = Modifier.size(16.dp))

            RegularButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = AppText.login,
                onButtonClick = onLoginClick,
                cardColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                highlightColor = MaterialTheme.colorScheme.onPrimary
            )


            Spacer(modifier = Modifier.size(42.dp))

            Text(
                text = stringResource(id = AppText.continue_with),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row {

                RegularSmallButton(
                    modifier = Modifier,
                    icon = IconsContentVibe.IC_GOOGLE,
                    title = AppText.google,
                    onButtonClick = {
                        startGoogleSignIn(context = context, launcher)
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    highlightColor = MaterialTheme.colorScheme.secondary,
                    applyTintOnIcon = false
                )

                Spacer(modifier = Modifier.size(16.dp))

                RegularSmallButton(
                    modifier = Modifier,
                    icon = IconsContentVibe.IC_FORGOT,
                    title = AppText.forgot,
                    onButtonClick = sendResetPasswordLink,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    highlightColor = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                Text(
                    text = stringResource(id = AppText.dont_have_account),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary

                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .clickable {
                            redirectToSignupScreen(AppRoutes.SIGN_UP_SCREEN)
                        },
                    text = stringResource(id = AppText.create_now),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }

        }

    }


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
fun LoginUser(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToNextScreen: (String) -> Unit
) {

    when (val loginState = authViewModel.loginState) {
        is Resource.Loading -> {
            logd("Login: Loading")
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


@Composable
fun OneTabSignInWithGoogle(
    authViewModel: AuthViewModel,
    launch: (result: BeginSignInResult) -> Unit
) {

    when (val oneTabSignInResponse = authViewModel.onTabSignInWithGoogle) {
        is Resource.Loading -> {
            Log.d("CurrentTAG", "OneTabSignInWithGoogle: Loading")
            ProgressBar()
        }

        is Resource.Success -> {
            Log.d("CurrentTAG", "OneTabSignInWithGoogle: Success")
            val signInResult = oneTabSignInResponse.data
            signInResult?.let {
                LaunchedEffect(key1 = it) {
                    launch(it)
                }
            }
        }

        is Resource.Error -> {
            Log.d("CurrentTAG", "OneTabSignInWithGoogle: Failure ${oneTabSignInResponse.message}")
        }
    }

}
