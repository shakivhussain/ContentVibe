package com.shakiv.husain.instagramui.domain.service

import com.google.firebase.auth.AuthCredential
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<UserEntity>
    suspend fun authenticate(email: String, password: String)

    suspend fun signInWithCredential(authCredential: AuthCredential)
    suspend fun createAnonymousAccount()
    suspend fun deleteAccount()
    suspend fun signOut()





}