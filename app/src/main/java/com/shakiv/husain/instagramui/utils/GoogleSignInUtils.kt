package com.shakiv.husain.instagramui.utils

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.utils.extentions.logd

object GoogleSignInUtils {

    fun handleGoogleSignInResult(
        data: Intent?,
        signInWithCredential: (AuthCredential) -> Unit
    ) {
        try {
            data?.let {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                val account = task.getResult(ApiException::class.java)!!
                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                signInWithCredential(credential)
            }

        } catch (e: Exception) {
            logd("Failed AuthScreen: ${e.message} ")
            e.printStackTrace()
        }
    }


    fun startGoogleSignIn(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val token = context.resources.getString(R.string.default_web_client_id)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()

        val googleSignClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignClient.signInIntent)
    }

}