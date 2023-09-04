package com.shakiv.husain.instagramui.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.shakiv.husain.instagramui.R


@Composable
fun AuthScreen(authViewModel: AuthViewModel = hiltViewModel()) {

    val token = stringResource(R.string.default_web_client_id)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        handleGoogleSignInResult(it.data, authViewModel)
    }

    val context = LocalContext.current

    Button(onClick = {
        startGoogleSignIn(token, context, launcher)
    }) {
        Text(text = "Login With Google")
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



