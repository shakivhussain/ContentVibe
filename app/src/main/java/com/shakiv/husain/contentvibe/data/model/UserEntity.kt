package com.shakiv.husain.contentvibe.data.model

import com.google.firebase.Timestamp

data class UserEntity(
    var userId: String = "",
    val isAnonymous: Boolean = true,
    var userName: String = "",
    val userAbout: String = "",
    val userProfile: String = "",
    val email: String = "",
    val profileUrl: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val description: String = "",
)


