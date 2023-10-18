package com.shakiv.husain.contentvibe.domain.model

data class UserPreferences(
    val userId: String,
    val userName : String,
    val needToShowOneTabSignIn : Boolean,
    val emailVerificationSend : Boolean
)
