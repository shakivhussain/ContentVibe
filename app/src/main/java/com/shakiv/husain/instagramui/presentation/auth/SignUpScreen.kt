package com.shakiv.husain.instagramui.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.domain.model.Resource
import com.shakiv.husain.instagramui.presentation.common.composable.BasicButton
import com.shakiv.husain.instagramui.presentation.common.composable.ConfirmPassword
import com.shakiv.husain.instagramui.presentation.common.composable.EmailField
import com.shakiv.husain.instagramui.presentation.common.composable.PasswordField
import com.shakiv.husain.instagramui.presentation.common.composable.ProgressBar
import com.shakiv.husain.instagramui.utils.extentions.fieldModifier
import com.shakiv.husain.instagramui.utils.extentions.getContext
import com.shakiv.husain.instagramui.R.string as AppText

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
) {

    val uiState = authViewModel.loginUiState

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        handleGoogleSignInResult(it.data, authViewModel)
    }



    SignUpScreenContent(
        uiState = uiState,
        onEmailNewValue = authViewModel::onEmailChange,
        onPasswordNewValue = authViewModel::onPasswordChange,
        onConfirmPasswordNewValue = authViewModel::onConfirmPassword,
        onSignUpClick = {
            authViewModel.onSignUpClick {
                Log.d("TAGAuth", "AuthScreen: Success")
            }
        },
        launcher
    )


    SignUp() {
        authViewModel.sendEmailVerification()
    }

}

@Composable
fun SignUpScreenContent(
    uiState: LoginUiState,
    onEmailNewValue: (String) -> Unit,
    onPasswordNewValue: (String) -> Unit,
    onConfirmPasswordNewValue: (String) -> Unit,
    onSignUpClick: () -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {

    val token = stringResource(R.string.default_web_client_id)
    val context = getContext()
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        EmailField(
            value = uiState.email, onNewValue = onEmailNewValue,
            modifier = fieldModifier
        )
        PasswordField(
            value = uiState.password, onNewValue = onPasswordNewValue, fieldModifier
        )
        ConfirmPassword(
            value = uiState.confirmPassword, onNewValue = onConfirmPasswordNewValue,
            fieldModifier
        )

        BasicButton(text = AppText.login_register) {

            onSignUpClick()

        }

        Button(
            modifier = Modifier.padding(),
            onClick = {
                startGoogleSignIn(token, context, launcher)
            }) {
            Text(text = "Login With Google")
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

private fun startGoogleSignIn(
    token: String, context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(token)
            .requestEmail().build()

    val googleSignClient = GoogleSignIn.getClient(context, gso)
    launcher.launch(googleSignClient.signInIntent)
}


private fun handleGoogleSignInResult(
    data: Intent?, authViewModel: AuthViewModel
) {
    try {
        data?.let {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it)
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            authViewModel.signInWithCredential(credential)
            Log.d("AuthTAG", "Success AuthScreen: ${account.displayName.toString()} ")
        }

    } catch (e: Exception) {
        Log.d("AuthTAG", "Failed AuthScreen: ${e.message} ")
        e.printStackTrace()
    }
}



