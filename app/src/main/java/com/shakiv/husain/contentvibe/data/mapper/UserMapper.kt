package com.shakiv.husain.contentvibe.data.mapper

import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.shakiv.husain.contentvibe.R
import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.User
import com.shakiv.husain.contentvibe.utils.AppUtils
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_CREATED_AT
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_DESCRIPTION
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_USER_NAME
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_EMAIL
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_IS_ANONYMOUS
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_PROFILE_URL
import com.shakiv.husain.contentvibe.utils.FirebaseConstants.KEY_USER_ID
import com.shakiv.husain.contentvibe.utils.extentions.random


fun UserEntity.toUser(): User{
    return User(
        userId = userId,
        userName = userName,
        profileUrl = profileUrl,
        email = email,
        userProfile = profileUrl,
        createdAt = createdAt,
        userDescription = description,
        isAnonymous = isAnonymous,
    )

}

fun FirebaseUser.toUser() = mapOf(
    KEY_USER_ID to uid,
    KEY_USER_NAME to displayName,
    KEY_EMAIL to email,
    KEY_PROFILE_URL to photoUrl?.toString(),
    KEY_IS_ANONYMOUS to isAnonymous,
    KEY_CREATED_AT to FieldValue.serverTimestamp(),
    KEY_DESCRIPTION to AppUtils.motivationalQuotes.getOrNull((0..19).random()).orEmpty()
)