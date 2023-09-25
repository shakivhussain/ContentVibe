package com.shakiv.husain.contentvibe.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class User(
    var userId: String = "",
    var userName: String = "",
    val userProfile: String = "",
    val email: String = "",
    val profileUrl: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val userDescription: String = "",
    val isAnonymous: Boolean = true,
)
