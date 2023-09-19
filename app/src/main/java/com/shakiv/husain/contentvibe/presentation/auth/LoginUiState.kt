package com.shakiv.husain.contentvibe.presentation.auth

data class LoginUiState(
    var email: String="",
    val password: String="",
    val confirmPassword : String=""
)