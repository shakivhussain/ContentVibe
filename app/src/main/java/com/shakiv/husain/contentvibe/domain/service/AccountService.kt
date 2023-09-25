package com.shakiv.husain.contentvibe.domain.service

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.Resource
import com.shakiv.husain.contentvibe.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<UserEntity>
    suspend fun getUserById(userId : String): UserEntity?
    suspend fun sendEmailVerification()
    suspend fun sendResetPasswordLink(email: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun signUpWithEmail(email: String, password: String)
    suspend fun signInWithCredential(authCredential: AuthCredential)
    suspend fun oneTabSignInWithGoogle() : Resource<BeginSignInResult>
    suspend fun createAnonymousAccount()
    suspend fun deleteAccount()
    suspend fun signOut()





}