package com.shakiv.husain.contentvibe.domain.model

data class User(
    var userId: String = "",
    var userName: String = "",
    val userProfile: String = "",
    val email: String = "",
    val isEmailVerified : Boolean = false,
    val profileUrl: String = "",
    val createdAt: String = "",
    val userDescription: String = "",
    val isAnonymous: Boolean = true,
)
