package com.shakiv.husain.instagramui.data.remote.imp

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.shakiv.husain.instagramui.data.model.UserEntity
import com.shakiv.husain.instagramui.domain.service.AccountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImp @Inject constructor(private val auth: FirebaseAuth) : AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null
    override val currentUser: Flow<UserEntity>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener {
                this.trySend(
                    auth.currentUser?.let {
                        UserEntity(userId = it.uid, isAnonymous = it.isAnonymous)
                    }
                        ?: UserEntity()
                )
            }
        }


    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()
        createAnonymousAccount()
    }

    override suspend fun signInWithCredential(authCredential: AuthCredential) {

        try {
            auth.signInWithCredential(authCredential).await()
        } catch (e: Exception) {

        }
    }


}