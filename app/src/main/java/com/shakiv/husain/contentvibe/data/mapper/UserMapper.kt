package com.shakiv.husain.contentvibe.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.User
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_CREATED_AT
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_DISPLAY_NAME
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_EMAIL
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_IS_ANONYMOUS
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_PROFILE_URL


fun UserEntity.toUser(): User{
    return User(
        id = userId,
        isAnonymous = isAnonymous
    )
}

fun FirebaseUser.toUser() = mapOf(
    KEY_DISPLAY_NAME to displayName,
    KEY_EMAIL to email,
    KEY_PROFILE_URL to photoUrl?.toString(),
    KEY_IS_ANONYMOUS to isAnonymous,
    KEY_CREATED_AT to FieldValue.serverTimestamp()
)