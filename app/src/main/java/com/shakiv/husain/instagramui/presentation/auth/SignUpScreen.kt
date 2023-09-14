package com.shakiv.husain.instagramui.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.domain.model.Resource
import com.shakiv.husain.instagramui.presentation.app.HomeDestination
import com.shakiv.husain.instagramui.presentation.common.composable.ConfirmPassword
import com.shakiv.husain.instagramui.presentation.common.composable.EmailField
import com.shakiv.husain.instagramui.presentation.common.composable.PasswordField
import com.shakiv.husain.instagramui.presentation.common.composable.ProgressBar
import com.shakiv.husain.instagramui.presentation.common.composable.RegularButton
import com.shakiv.husain.instagramui.presentation.common.composable.RegularSmallButton
import com.shakiv.husain.instagramui.utils.GoogleSignInUtils.handleGoogleSignInResult
import com.shakiv.husain.instagramui.utils.GoogleSignInUtils.startGoogleSignIn
import com.shakiv.husain.instagramui.utils.IconsInstagram
import com.shakiv.husain.instagramui.utils.ImageUtils
import com.shakiv.husain.instagramui.utils.extentions.fieldModifier
import com.shakiv.husain.instagramui.utils.extentions.getContext
import com.shakiv.husain.instagramui.utils.extentions.logd
import com.shakiv.husain.instagramui.R.string as AppText

@Preview
@Composable
fun PreviewSignUpScreen() {

    val uiState = LoginUiState(

    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {

    }
    SignUpScreenContent(
        uiState = uiState,
        onEmailNewValue = {},
        onPasswordNewValue = {},
        onConfirmPasswordNewValue = {},
        onSignUpClick = { /*TODO*/ },
        navigateToLoginScreen = {},
        launcher = launcher
    )

}

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen : () -> Unit,
    navigateToHomeScreen: (String) -> Unit,
    ) {

    val uiState = authViewModel.loginUiState

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        handleGoogleSignInResult(it.data) { credential ->
            authViewModel.signInWithCredential(credential)
        }
    }



    SignUpScreenContent(
        uiState = uiState,
        onEmailNewValue = authViewModel::onEmailChange,
        onPasswordNewValue = authViewModel::onPasswordChange,
        onConfirmPasswordNewValue = authViewModel::onConfirmPassword,
        onSignUpClick = {
            authViewModel.onSignUpClick {
                logd("AuthScreen: Success")
            }
        },
        navigateToLoginScreen = navigateToLoginScreen,
        launcher
    )


    SignUp() {
        authViewModel.sendEmailVerification()
    }

    GoogleSignIn(authViewModel, isGoogleSignInSuccessfully = { navigateToHomeScreen(HomeDestination.route) })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    uiState: LoginUiState,
    onEmailNewValue: (String) -> Unit,
    onPasswordNewValue: (String) -> Unit,
    onConfirmPasswordNewValue: (String) -> Unit,
    onSignUpClick: () -> Unit,
    navigateToLoginScreen : () -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {

    val context = getContext()
    val fieldModifier = Modifier.fieldModifier()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ImageUtils.setImage(
                modifier = Modifier.fillMaxWidth(.5F),
                imageId = IconsInstagram.IC_SIGN_UP
            )

            Spacer(modifier = Modifier.size(16.dp))


            Text(
                text = "SignUp",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier
                    .size(45.dp)
                    .fillMaxWidth()
            )


            EmailField(
                value = uiState.email, onNewValue = onEmailNewValue,
                modifier = fieldModifier
            )
            PasswordField(
                value = uiState.password, onNewValue = onPasswordNewValue, fieldModifier
            )
            ConfirmPassword(
                value = uiState.confirmPassword,
                onNewValue = onConfirmPasswordNewValue,
                fieldModifier
            )

            Spacer(modifier = Modifier.size(42.dp))

            RegularButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = AppText.login,
                onButtonClick = onSignUpClick,
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
                    icon = IconsInstagram.IC_GOOGLE,
                    title = AppText.google,
                    onButtonClick = {
                        startGoogleSignIn(context, launcher)
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
                    icon = IconsInstagram.IcBack,
                    title = AppText.signup,
                    onButtonClick = navigateToLoginScreen,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    highlightColor = MaterialTheme.colorScheme.secondary
                )
            }



        }
    }




}

@Composable
fun SignUp(
    authViewModel: AuthViewModel = hiltViewModel(),
    sendVerificationLink: () -> Unit
) {
    when (val signUpState = authViewModel.signUpState) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserSignerUp = signUpState.data ?: false
            LaunchedEffect(key1 = isUserSignerUp) {
                if (isUserSignerUp) {
                    sendVerificationLink()
                }
            }
        }

        is Resource.Error -> {

        }
    }
}







