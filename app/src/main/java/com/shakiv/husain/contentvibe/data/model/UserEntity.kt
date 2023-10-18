package com.shakiv.husain.contentvibe.data.model

import androidx.annotation.Keep

@Keep
data class UserEntity(
    var userId: String = "",
    val isAnonymous: Boolean = true,
    var userName: String = "",
    val userAbout: String = "",
    val email: String = "",
    val isEmailVerified : Boolean = false,
    val profileUrl: String = "",
    val createdAt: String = "",
    val description: String = "",
)


