package com.shakiv.husain.instagramui.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.shakiv.husain.instagramui.R
import com.shakiv.husain.instagramui.utils.AppUtils.SIGN_IN_REQUEST
import com.shakiv.husain.instagramui.utils.AppUtils.SIGN_UP_REQUEST
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides fun auth() : FirebaseAuth =Firebase.auth

    @Provides fun firestore() : FirebaseFirestore = Firebase.firestore

    @Provides fun storage() : FirebaseStorage = Firebase.storage


    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)



    @Provides
    fun provideGoogleSignInClient(
        app : Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app,options)


    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.resources.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()

    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.resources.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(false)
        .build()

}