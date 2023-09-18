package com.shakiv.husain.contentvibe.data.remote.imp

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.perf.ktx.trace
import com.google.firebase.perf.metrics.Trace
import com.shakiv.husain.contentvibe.data.mapper.toUser
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.Resource
import com.shakiv.husain.contentvibe.domain.service.AccountService
import com.shakiv.husain.contentvibe.utils.AppUtils.SIGN_IN_REQUEST
import com.shakiv.husain.contentvibe.utils.AppUtils.SIGN_UP_REQUEST
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_USERS
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.ONE_TAB_SIGN_IN_TRACE
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.SIGNUP_TRACE
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class AccountServiceImp @Inject constructor(
    private val auth: FirebaseAuth,
    private val oneTabClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private val signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private val signUpRequest: BeginSignInRequest,
    private val fireStore: FirebaseFirestore,
    ) : AccountService {
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
                    } ?: UserEntity()
                )
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }


    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }


    override suspend fun signUpWithEmail(email: String, password: String): Unit =
        trace(SIGNUP_TRACE) {
//            val credential = EmailAuthProvider.getCredential(email, password)
//            auth.currentUser!!.linkWithCredential(credential).await()!!
            auth.createUserWithEmailAndPassword(email, password)
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
            val authResult = auth.signInWithCredential(authCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

            if (isNewUser) {
                addUserToFireStore()
            }

        } catch (e: Exception) {
        }
    }

    private suspend fun addUserToFireStore() {
        auth.currentUser?.apply {
            val user = this.toUser()
            fireStore.collection(KEY_USERS).document(uid).set(user).await()
        }
    }


    override suspend fun oneTabSignInWithGoogle(): Resource<BeginSignInResult> {
        trace(ONE_TAB_SIGN_IN_TRACE) {
            try {
                val signInResult = oneTabClient.beginSignIn(signInRequest).await()
                return Resource.Success(signInResult)
            } catch (e: Exception) {
                try {
                    val signUpResult = oneTabClient.beginSignIn(signUpRequest).await()
                    return Resource.Success(signUpResult)
                } catch (e: Exception) {
                    return Resource.Error(message = e.message.toString())
                }
            }
        }
    }


    override suspend fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
    }

    override suspend fun sendResetPasswordLink(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }


    inline fun <T> trace(name: String, block: Trace.() -> T): T = Trace.create(name).trace(block)


}